import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.apache.lucene.document.Document;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProcesamientoDatos {
	private ConexionMySQL cmsql;
	private IndiceInvertido indice;
	private ConexionNeo4j neo4j;
	private JsonArray nodos;
	private JsonArray links;
	private static final String nombreArchivoSentimiento="C:\\Users\\Abraham\\Desktop\\Sentimientos.txt";
	
	public ProcesamientoDatos(){
		cmsql= new ConexionMySQL("root", "speeddemon1");
		indice= new IndiceInvertido();
		neo4j= new ConexionNeo4j("neo4j", "speeddemon1");
		System.out.println("cantidad de documentos: "+String.valueOf(indice.cantidadDocumentos));
		nodos= new JsonArray();
		links= new JsonArray();
		crearOpinionesMetricasTodosCandidatos();
		//Una vez creadas todas las opiniones se escribe el archivo.json
		escribirArchivoJson(nodos, links);
	}
	
	public static void main(String[] args){
		new ProcesamientoDatos();
		
	}
	
	//metodo que crea todas las opiniones de todos los candidatos en la base de datos mysql
	public void crearOpinionesMetricasTodosCandidatos(){
		//Se obtienen los ids de los candidatos en el sistema
		int[] idsCandidatos =cmsql.obtenerIdsCandidatos();
		//Para cada uno de los ids de los candidatos
		for(int i=0; i < idsCandidatos.length; i++){
			//Se crean las opiniones acerca del candidato segun su id en la base de datos mysql
			crearOpinionesCandidato(idsCandidatos[i]);
		}
		//double total=0;
		//una vez creadas las opiniones, se procede a calcular las aprobaciones de cada uno de los candidatos
		for(int i=0;i < idsCandidatos.length;i++){
			double aprob= calcularAprobacionNacional(idsCandidatos[i]);
			//total=total+aprob;
			System.out.println("La aprobacion del candidato: "+idsCandidatos[i]+" es: "+aprob);
			Date date= new Date();
	    	long miliseg= date.getTime();
	    	Timestamp fechaCreacionAprobacion = new Timestamp(miliseg);
			int confirmacion = cmsql.insertarMetrica("Aprobacion Nacional", aprob, fechaCreacionAprobacion, idsCandidatos[i]);
			if(confirmacion==1){
				System.out.println("Se ha calculado con exito la aprobacion nacional para el candidato: "+idsCandidatos[i]);
			}
			else if(confirmacion==2){
				System.out.println("Se calculo la aprobacion para el candidato: "+idsCandidatos[i] +", pero no se pudo establecer la relacion");
			}
			else{
				System.out.println("Error en la insercion de la aprobacion a la base de datos");
			}
		}
		//System.out.println("total: "+total);
		//Se deben insertar las aprobaciones nacionales de cada candidato en forma de metricas en la bd mysql
		
	}
	
	
	
	
	
	//metodo que inserta todas las "opiniones" sobre un candidato a la base de datos mysql 
	public void crearOpinionesCandidato(int idCandidato){
		String nombreCandidato = cmsql.obtenerNombreCandidato(idCandidato);
		agregarNodoJson(nombreCandidato,idCandidato,true);
		//Se obtiene la cuenta oficial de twitter del candidato con ese id
		String cuentaCandidato = cmsql.obtenerCuentaCandidato(idCandidato);
		//se obtienen todas las keywords del candidato por su id
		ArrayList<String> keywordsCandidato = cmsql.obtenerKeywordsCandidato(idCandidato);
		/*System.out.print("keys del candidato: "+ String.valueOf(idCandidato)+"\n");
		for(int i=0; i< keywordsCandidato.size();i++){
			System.out.print(keywordsCandidato.get(i)+"\n");
		}
		*/
		//Para cada keyword
		//System.out.println("Comienza la busqueda");
		for(int k=0; k < keywordsCandidato.size(); k++){
			String keyword= keywordsCandidato.get(k);
			//System.out.println("SOy key: "+keyword);
			//Se busca en el indice invertido los documentos que la contengan en el texto, para 
			//crear una opinion en base a los tweets
			Document[] resultados=null;
			//Se consulta por la keyword dentrl del texto de un tweeet
			resultados = indice.buscarDocumentosPorCampo("text", keyword,indice.cantidadDocumentos);
			//Se leen los resultados de la busqueda
			if(resultados!=null){
				//System.out.println("cantidad de documentos que contienen la key: "+ keyword +" : "+ resultados.length);
				for(int i=0; i < resultados.length; i++){
					//opinion representa un tweet de la base de datos mongo, que estaba procesado
					Document opinion = resultados[i];
					/*
					System.out.println("leyendo los documento buscados:");
					System.out.println("id: "+opinion.get("id"));
					System.out.println("text: "+opinion.get("text"));
					System.out.println("rt_count: "+opinion.get("retweet_count"));
					System.out.println("fav_count: "+opinion.get("fav_count"));
					System.out.println("is_rtd: "+opinion.get("is_retweeted"));
					System.out.println("pais: "+opinion.get("country_location"));
					System.out.println("region: "+opinion.get("region_location"));
					System.out.println("ciudad: "+opinion.get("ciudad_location"));
					System.out.println("created_at: "+opinion.get("created_at"));
					System.out.println("in_reply_to_user_screen_name: "+opinion.get("in_reply_to_user_screen_name"));
					System.out.println("in_reply_to_user_id: "+opinion.get("in_reply_to_user_id"));
					System.out.println("in_reply_to_status_id: "+ opinion.get("in_reply_to_status_id"));
					System.out.println("user_id: "+ opinion.get("user_id"));
					System.out.println("user_screen_name: "+opinion.get("user_screen_name"));
					System.out.println("user_name: "+opinion.get("user_name"));
					System.out.println("others_users_mentions: "+opinion.get("others_users_mentions"));
					*/
					//Se analiza el texto del tweet para ver si es positivo o negativo
					int sentimiento = analizarSentimiento(opinion.get("text"));
					//Si se analizo correctamente, se procede a guardalo en la base de datos Mysql
					if(sentimiento!=-1){
						//System.out.print("Analice sentimientos");
						int cantidadRetweets = Integer.parseInt(opinion.get("retweet_count"));
						int cantidadLikes = Integer.parseInt(opinion.get("fav_count"));
					    Timestamp fechaCreacion = Timestamp.valueOf(opinion.get("created_at"));
					    String pais = opinion.get("country_location");
					    String region= opinion.get("region_location");
					    String ciudad = opinion.get("city_location");
					    String autor = opinion.get("user_screen_name");
					    //Se debe ver si menciona al candidato o no y eso almacenarlo en neo4j
					    //Para despues mostrar el grafo generado en la aplicacon web
					    //variable de tipo int o boolean que indica que si se menciona al candidato
					    boolean mencionaCandidato=false;
					    boolean respuestaCandidato=false;
					    if(opinion.get("others_users_mentions")==null){
					    	mencionaCandidato=false;
					    }
					    else{
					    	String[] menciones = opinion.get("others_users_mentions").split(",");
						    for(int j=0; j < menciones.length; j++){
						    	if(menciones[j].equals(cuentaCandidato)){
						    		mencionaCandidato=true;
						    		//hay que ver si el candidato respondio a la mencion, para eso hay
						    		//que buscar en todos los tweets que ha realizado el candidato
						    		//y ver si el in_reply_tostatus_id es igual al id de este tweet opinion
						    		Document[] tweetsCandidato = indice.buscarDocumentosPorCampo("user_screen_name", cuentaCandidato,indice.cantidadDocumentos);
						    		for(int n=0; n < tweetsCandidato.length; n++){
						    			System.out.println("DOCUMENTOS ESCRITOS POR EL CANDIDATO: "+cuentaCandidato+": "+tweetsCandidato.length);
						    			long idTweetOpinion= Long.parseLong(opinion.get("id"));
						    			long inReplyToStatusId= Long.parseLong(tweetsCandidato[n].get("in_reply_to_status_id"));
						    			if(inReplyToStatusId==idTweetOpinion){
						    				//Si son iguales, es que el candidato respondio al tweet opinion
						    				respuestaCandidato=true;
						    				Timestamp fechaRespuesta = Timestamp.valueOf(tweetsCandidato[n].get("created_at"));
						    				//Aqui se crean los nodos candidato y usuario
						    				//y su relacion de interaccion o mencion/respuesta
						    				//y se guarda en neo4j
						    				//autor es el nombre de usuario del que emite la opinion
						    				//cuentaCandidato es el nombre de usuario del candidato en twitter
						    				//sentimiento es el feel de la relacion
						    				String sent=null;
						    				if(sentimiento==1){
						    					sent="positivo";
						    				}
						    				else if(sentimiento==0){
						    					sent="negativo";
						    				}
						    				else if(sentimiento==2){
						    					sent="neutro";
						    				}
						    				agregarNodoJson(autor,17,false);
						    				agregarLinkJson(autor,nombreCandidato,sent,true);
						    				agregarLinkJson(autor,nombreCandidato,sent,false);
						    				/*
						    				String consulta = "CREATE (u:Usuario { nombreTwitter: '"+autor+"'}),(c:Candidato { nombreTwitter: '"+cuentaCandidato+"'}),"
						    									+"(u)-[:MENCIONA {sentimiento :'"+sent+"', fecha : "+fechaCreacion+"}]->(c),"
						    									+"(c)-[:RESPONDE {fecha : "+fechaRespuesta+"}]->(u)";
						    				neo4j.consulta(consulta);
						    				*/
						    			}
						    		}
						    	}
						    }
						    
						 
						 
					    }
					    int confirmacion = cmsql.insertarOpinion(idCandidato, fechaCreacion, sentimiento, pais, region, ciudad, cantidadRetweets, cantidadLikes, autor, mencionaCandidato, respuestaCandidato);
					    if(confirmacion != 1){
					    	System.out.println("Error al insertar opinion: "+opinion.get("text"));
					    }
					    else{
					    	System.out.println("opinion: "+ opinion.get("text"));
					    }
					}
				}
			}
			
		}
		
	}
	
	//Metodo que chequea si el texto de un tweet tiene sentimiento positivo(1) o negativo(0)
	//Se va realizando un conteo de cuantas palabras positivos y negativas tiene el texto del tweet
	//el numero mayor define si es positivo o negativo el contenido del tweet
	public int analizarSentimiento(String text){
		//System.out.println("Analizando sentimientos...");
		File archivo= new File(nombreArchivoSentimiento);
		int cantidadPalabrasPositivas=0;
		int cantidadPalabrasNegativas=0;
		try{
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while(linea!=null){
				String palabraComparacion=null;
				//se debe armar la palabra sin los guiones bajos para encontrarla dentro del texto
				//campos contiene: en la primera posicion la palabra con guiones
				//y en la segunda si es positive o negative
				String[] campos = linea.split(" ");
				//palabra contiene el intento de separacion de la palabra por guiones bajos
				//En caso de que no se pueda separar, solo se retorna la misma palabra que es
				//Una sola sin guiones
				//String[] palabra =  campos[0].split("_");
				//Si la palabra tiene al menos un guin bajo
				if(campos[0].indexOf("_")!=-1){
					palabraComparacion = campos[0].replace("_", " ");
				}
				//sino
				else{
					//la palabra es igual a la que se leyo del archivo
					palabraComparacion=campos[0];
				}
				/*
				//Si palabra no es igual a la palabra con guiones, es decir, si se separo
				//eso quiere decir que es mas de un palabra y es una frase
				//Por lo que se debe armar
				if(!palabra.equals(campos[0])){
					for(int i=0; i < palabra.length; i++){
						//Si no es la ultima palabra a agregar
						//Se agrega la palabra mas un espacio
						if(i!=palabra.length-1){
						palabraComparacion=palabraComparacion+palabra[i]+" ";
						}
						//Sino, si es la ultima palabra, se agrega solo la palabra y no el espacio
						else{
							palabraComparacion= palabraComparacion+palabra[i];
						}
					}
				}
				*/
				//Sino, significa que la palabra es una sola, por lo que esa sera la palabra
				//A buscar en el texto del tweet
				/*else{
					//Se asigna a la palabra como la misma que se leyo del archivo
					palabraComparacion=campos[0];
				}*/
				//Si la palabra de comparacion o a buscar en el texto
				//Esta en el texto, se clasifica segun lo que dice el archivo(positive o negative)
				//System.out.println("palabra formada: "+palabraComparacion);
				if(text.indexOf(palabraComparacion)!=-1){
					// 1 es positive
					//System.out.println("Se encontro la palabra: "+palabraComparacion+" en el texto");
					if(campos[1].equals("positive")){
						cantidadPalabrasPositivas=cantidadPalabrasPositivas+1;
					}
					else{
						//Si no, es negative y es 0
						cantidadPalabrasNegativas=cantidadPalabrasNegativas+1;
					}
				}
				linea=br.readLine();
			}
			/*System.out.println("Para texto: "+text);
			System.out.println("palabras positivas: "+cantidadPalabrasPositivas);
			System.out.println("palabras negativas: "+cantidadPalabrasNegativas);*/
			//Una vez revisadas todas las palabras del archivo
			//Si las cantidades de las palabras son 0, significa que no contenia ninguna de las palabras
			//del lexico, por lo que se asigna un valor de sentimiento al azar entre 0 y 1
			if(cantidadPalabrasPositivas==0 && cantidadPalabrasNegativas==0){
					int random = (int) Math.floor(Math.random()*(1-0+1)+0);  // Valor entre 0 y 1, ambos incluidos.
					br.close();
					//return random;
					return 2;
			}
			//Sino
			else{
				//Signifca que si se encontraron palabras del diccionario en el texto
				//por lo que se vee cual es la mayor cantidad de palabras encontradas
				if(cantidadPalabrasPositivas > cantidadPalabrasNegativas){
					br.close();
					return 1;
				}
				else if(cantidadPalabrasPositivas < cantidadPalabrasNegativas){
					br.close();
					return 0;
				}
				//si son iguales las cantidades, se retorna un numero al azar entre 0 y 1 
				else{
					int random = (int) Math.floor(Math.random()*(1-0+1)+0);  // Valor entre 0 y 1, ambos incluidos.
					br.close();
					return random;
				}
			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		//Si se retorna el menos 1, signifca que hay un error 
		return -1;
	}
	
	//metodo que calcula la aprobacion nacional de un candidato
	//la formula es: aprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1
	//donde 
	/* tpos_total = total de tweets positivos entre todos los candidatos
		tpos[i] = tpos_candidato[i]/tpos_total

	likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos
	likes[i] = likes_candidato[i]/likes_total

	rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos
	rts[i] = rts_candidato[i]/rts_total
	 */
	public double calcularAprobacionNacional(int idCandidato){
		double aprobacion = 0;
		double cte1= 0.7;
		double cte2 = 0.2;
		double cte3= 0.1;
		double opPosTotal = (double) cmsql.obtenerCantidadOpinionesPositivas();
		System.out.println(opPosTotal);
		double opPosCand = (double) cmsql.obtenerCantidadOpinionesPositivasCand(idCandidato);
		System.out.println(opPosCand);
		double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesPositivas();
		System.out.println(opPosLikesTotal);
		double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesPositivasCand(idCandidato);
		System.out.println(opPosLikesCand);
		double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesPositivas();
		System.out.println(opPosRtsTotal);
		double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesPositivasCand(idCandidato);
		System.out.println(opPosRtsCand);
		double opinionesPosCand = opPosCand/opPosTotal;
		System.out.println("opniones: "+opinionesPosCand);
		double likesPosCand = opPosLikesCand/opPosLikesTotal;
		System.out.println("likes: "+likesPosCand);
		double rtsPosCand = opPosRtsCand/opPosRtsTotal;
		System.out.println("rts: "+rtsPosCand);
		aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
		return aprobacion;
	}
	
	public void agregarNodoJson(String nombre, int grupo, boolean esCandidato){
		JsonObject nuevoNodo = new JsonObject();
		nuevoNodo.addProperty("id",nombre);
		if(esCandidato){
			nuevoNodo.addProperty("group",grupo);
		}
		else{
			nuevoNodo.addProperty("group", 17);
		}
		System.out.println(nuevoNodo.toString());
		nodos.add(nuevoNodo);
	}
	
	public void agregarLinkJson(String nombreUser, String nombreCandidato, String feel,  boolean esMencion){
		JsonObject nuevoLink = new JsonObject();
		if(esMencion){
			nuevoLink.addProperty("source",nombreUser);
			nuevoLink.addProperty("target",nombreCandidato);
			nuevoLink.addProperty("feel",feel);
		}
		else{
			nuevoLink.addProperty("target",nombreUser);
			nuevoLink.addProperty("source",nombreCandidato);
			nuevoLink.addProperty("feel", "neutro");
		}
		System.out.println(nuevoLink.toString());
		links.add(nuevoLink);
	}
	
	public void escribirArchivoJson(JsonArray nodos, JsonArray links){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try{
        	String directorio="datos.json";
        	fichero = new FileWriter(directorio);
    		pw = new PrintWriter(fichero);
        	JsonObject jsonFinal= new JsonObject();
        	jsonFinal.add("nodes", nodos);
        	jsonFinal.add("links", links);
        	pw.print(jsonFinal.toString());
         }catch (Exception e) {
            e.printStackTrace();
         }
	}
}
