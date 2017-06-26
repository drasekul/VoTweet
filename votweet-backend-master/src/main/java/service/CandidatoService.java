package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.lucene.document.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import Utilidades.IndiceInvertido;
import Utilidades.MongoDB;
import Utilidades.Neo4j;
import facade.CandidatoFacade;
import facade.OpinionFacade;
import model.Candidato;
import model.Opinion;

@Path("/candidatos")
public class CandidatoService {
	
	@EJB 
	CandidatoFacade candidatoFacadeEJB;
	@EJB 
	OpinionFacade opinionFacadeEJB;
	
	Logger logger = Logger.getLogger(CandidatoService.class.getName());
	

	
	@GET
	@Produces({"application/json;charset=utf-8"})
	public List<Candidato> findAll(){
		return candidatoFacadeEJB.findAll();
	}
	
	@GET
	@Path("{id}/borrar")
	@Produces({"application/json;charset=utf-8"})
	public String eliminarCandidatoId(@PathParam("id") Integer id){
		String respuesta;
		if(candidatoFacadeEJB.find(id)==null){
			respuesta="No existe dicho candidato en la base de datos";
			return respuesta;
		}
		try{
			candidatoFacadeEJB.remove(candidatoFacadeEJB.find(id));
			respuesta="Se ha eliminado el candidato con exito";
			return respuesta;
		}
		catch(Exception e){
			respuesta="Error desconocido";
			return respuesta;
		}
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json;charset=utf-8"})
    public Candidato find(@PathParam("id") Integer id) {
        return candidatoFacadeEJB.find(id);
    }
	
	
	@POST
    @Consumes({"application/json;charset=utf-8"})
    public String create(Candidato entity) {
		String respuesta;
		candidatoFacadeEJB.create(entity);
		respuesta="Usuario creado con exito";
		return respuesta;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json;charset=utf-8"})
    public void edit(@PathParam("id") Integer id, Candidato entity) {
    	entity.setCdtoId(id.intValue());
        candidatoFacadeEJB.edit(entity);
    }
    
	//metodo que retorna un json que contiene los nodos y relaciones que tiene
	//un candidato con los 20 usuarios con m�s seguidores
    //LA IMPLEMENTACION DE LA INTERRACION ES EN BASE A CUANDO UN USUARIO MENCIONA A CANDIDATO
    //POR LO QUE EN EL SISTEMA, EL CANDIDATO SOLO CONTRAMENCIONA O RESPONDE A QUIEN LO HAYA MENCIONADO
	@GET
	@Path("{id}/interaccionCandidato")
	@Produces({"application/json;charset=utf-8"})
	public JsonObject obtenerInteraccionCandidato(@PathParam("id") Integer idCandidato){
		Candidato candidato = candidatoFacadeEJB.find(idCandidato);
		String respuesta=null;
		JsonObject jsonRespuesta=null;
		if(candidato!=null){
			Neo4j neo4j = new Neo4j("neo4j","speeddemon1");
			JsonObjectBuilder builderRespuesta = Json.createObjectBuilder();
			JsonArrayBuilder builderNodos = Json.createArrayBuilder();
			JsonArrayBuilder builderRelaciones = Json.createArrayBuilder();
			JsonObjectBuilder builderNodo = Json.createObjectBuilder();
			String screenNameCandidato = candidato.getCdtoCuentaTwitter();
			String nameCandidato = candidato.getCdtoNombre();
			//se agrega el nodo del candidato
			builderNodo.add("id", nameCandidato);
			builderNodo.add("group",idCandidato);
			JsonObject jsonObjectNodo = builderNodo.build();
			builderNodos.add(jsonObjectNodo);
			ArrayList<String[]> datosRelacionesMencion = neo4j.obtenerDatosRelacionesMencion(screenNameCandidato);
			ArrayList<String[]> datosRelacionesRespuesta = neo4j.obtenerDatosRelacionesRespuesta(screenNameCandidato);
			//Se agregan los nodos usuarios al json, obtenidos desde la relacion de mencion 
			if(datosRelacionesMencion!=null){
				ArrayList<String> agregados= new ArrayList<String>();
				//para cada relacion de mencion
				for(int i=0; i < datosRelacionesMencion.size(); i++){
					String[] datosRelacionMencion = datosRelacionesMencion.get(i);
					//Se agrega el usuario al json array de nodos
					//Si no ha sido agregado, se agrega al json y a la lista de agregados
					if(agregados.indexOf(datosRelacionMencion[0])==-1){
						builderNodo = Json.createObjectBuilder();
						builderNodo.add("id", datosRelacionMencion[0]);
						builderNodo.add("group", -1);
						jsonObjectNodo = builderNodo.build();
						builderNodos.add(jsonObjectNodo);
						agregados.add(datosRelacionMencion[0]);
					}
					//Se agrega la relacion de mencion al json array de relaciones
					JsonObjectBuilder builderRelacion = Json.createObjectBuilder();
					builderRelacion.add("source", datosRelacionMencion[0]);
					builderRelacion.add("target", nameCandidato);
					//builderRelacion.add("followersUser", datosRelacionMencion[1]);
					String feel=null;
					int sentimiento = Integer.parseInt(datosRelacionMencion[2]);
					if(sentimiento==1){
						feel="positivo";
					}
					else if(sentimiento==0){
						feel="negativo";
					}
					else if(sentimiento==2){
						feel="neutro";
					}
					builderRelacion.add("feel", feel);
					//builderRelacion.add("fecha", datosRelacionMencion[3]);
					JsonObject jsonObjectRelacion = builderRelacion.build();
					builderRelaciones.add(jsonObjectRelacion);
				}
			}
			
			//Se agregan los datos de las relaciones de respuesta al json de relaciones 
			if(datosRelacionesRespuesta!=null){
				//para cada relacion de mencion
				for(int i=0; i < datosRelacionesRespuesta.size(); i++){
					String[] datosRelacionRespuesta = datosRelacionesRespuesta.get(i);
					//NO ES NECESARIO AGREGAR DE NUEVO USUARIOS
					//YA QUE EL SISTEMA MODELA LA INTERRACION 
					//SOLO CUANDO UN USUARIO MENCIONA AL CANDIDATO
					//POR LO QUE ES IMPOSIBLE QUE EL CANDIDATO RESPONDA
					//A UN USUARIO QUE NO LO HAYA MENCIONADO
					//Se agrega la relacion de mencion al json array de relaciones
					JsonObjectBuilder builderRelacion = Json.createObjectBuilder();
					builderRelacion.add("source", nameCandidato);
					builderRelacion.add("target", datosRelacionRespuesta[0]);
					//builderRelacion.add("followersUser", datosRelacionRespuesta[1]);
					String feel=null;
					int sentimiento = Integer.parseInt(datosRelacionRespuesta[2]);
					if(sentimiento==1){
						feel="positivo";
					}
					else if(sentimiento==0){
						feel="negativo";
					}
					else if(sentimiento==2){
						feel="neutro";
					}
					builderRelacion.add("feel", feel);
					//builderRelacion.add("fecha", datosRelacionRespuesta[3]);
					JsonObject jsonObjectRelacion = builderRelacion.build();
					builderRelaciones.add(jsonObjectRelacion);
				}
			}
			//Se construyen los json array de nodos y relaciones
			JsonArray jsonArrayNodos = builderNodos.build();
			JsonArray jsonArrayRelaciones = builderRelaciones.build();
			builderRespuesta.add("nodes", jsonArrayNodos);
			builderRespuesta.add("links", jsonArrayRelaciones);
			jsonRespuesta = builderRespuesta.build();
			//respuesta=jsonRespuesta.toString();
			neo4j.cerrarConexion();
			return jsonRespuesta;
			//return respuesta;
		}
		return jsonRespuesta;
		
	}
	
	//Metodo que obtiene los tweets mas importantes ( y alguno de sus datos)
	//que se han escrito acerca de un candidato
	//los tweets se rastrean en base a las opiniones
	//obteniendo todas las opiniones emitidas para un candidato
	//y luego en base a la combinacion unica de autor, fecha de creacion
	//se consulta el texto del tweet en el indice
	@GET
	@Path("{id}/tweetsImportantes")
	@Produces({"application/json;charset=utf-8"})
	public JsonArray obtenerTweetsImportantes(@PathParam("id") Integer idCandidato){
		JsonArray respuesta=null;
		JsonArrayBuilder builderArray = Json.createArrayBuilder();
		Candidato candidato = candidatoFacadeEJB.find(idCandidato);
		String cuentaCandidato = candidato.getCdtoCuentaTwitter();
		List<Opinion> opiniones = opinionFacadeEJB.encontrar20opinionesImportantesCandidato(idCandidato, cuentaCandidato);
		//IndiceInvertido indice = new IndiceInvertido();
		MongoDB mongo = new MongoDB();
			for(int i=0; i < opiniones.size(); i++){
				Opinion opinion = opiniones.get(i);
				if(opinion.getCdtoId()==idCandidato){
					JsonObjectBuilder builderObject = Json.createObjectBuilder();
					String autor = opinion.getOpinionAutor();
					int cantidadRt = opinion.getOpinionRetweets();
					int cantidadLikes = opinion.getOpinionLikes();
					String region = opinion.getOpinionRegionUbicacion();
					String ciudad = opinion.getOpinionCiudadUbicacion();
					Timestamp fecha = opinion.getOpinionFecha();
					BasicDBObject query = new BasicDBObject();
					query.put("user_screen_name", autor);
					DBCursor cursor = mongo.tweets.find(query);
					while(cursor.hasNext()){
						DBObject tweet = cursor.next();
						if(tweet.get("created_at").toString().equals(fecha.toString())){
							String texto = tweet.get("text").toString();
							builderObject.add("autor", autor);
							builderObject.add("fecha", fecha.toString());
							builderObject.add("texto", texto);
							builderObject.add("region", region);
							builderObject.add("ciudad", ciudad);
							builderObject.add("cantidadRt", cantidadRt);
							builderObject.add("cantidadLikes", cantidadLikes);
							JsonObject jsonObject = builderObject.build();
							builderArray.add(jsonObject);
						}
					}
				}
			}
	
		respuesta=builderArray.build();
		return respuesta;
				/*
				//Se buscan todas las opiniones escritas por el usuario en mysql
				//para poder saber cuantas ha escrito
				List<Opinion> opinionesEscritasPorUsuario = opinionFacadeEJB.opinionesEscritasPorUsuario(autor);
				//esta combinacion de atributos es unica
				//se buscan todos los documentos escritos por el usuario en el indice invertido
				//para poder obtener el texto del tweet
				Document[] documentos = indice.buscarDocumentosPorCampo("user_screen_name", autor,opinionesEscritasPorUsuario.size());
				return documentos;
				for(int j=0; j < documentos.length; j++){
					//En los resultados de la busqueda del indice, se obtiene la fecha de cada documento o tweet
					String fechaTweet = documentos[j].get("created_at");
					//si la fecha es del tweet en cuestion es la misma a la de la opinion
					//significa que se cumple la condicion de autor-fecha
					//por lo tanto es ese el tweet que representa la opinion
					//y por lo tanto se obtienen sus datos y se crea el objeto json
					//y se agrega al array 
					if(fechaTweet.equals(fecha.toString())){
						Document tweet = documentos[j];
						String texto = tweet.get("text");
						String region="sin localizacion";
						if(!tweet.get("region_location").equals(null)){
							region = tweet.get("region_location");
						}
						String ciudad="sin localizacion";
						if(!tweet.get("ciudad_location").equals(null)){
							region = tweet.get("ciudad_location");
						}
						int cantidadRt = Integer.parseInt(tweet.get("retweet_count"));
						int cantidadLikes = Integer.parseInt(tweet.get("fav_count"));
						builderObject.add("autor", autor);
						builderObject.add("fecha", fecha.toString());
						builderObject.add("texto", texto);
						builderObject.add("region", region);
						builderObject.add("ciudad", ciudad);
						builderObject.add("cantidadRt", cantidadRt);
						builderObject.add("cantidadLikes", cantidadLikes);
						JsonObject jsonObject = builderObject.build();
						builderArray.add(jsonObject);
					}
				}
				
			}
			*/

		//respuesta=builderArray.build();
		//return respuesta;
	}
}
