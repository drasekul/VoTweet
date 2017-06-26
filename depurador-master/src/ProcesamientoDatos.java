import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private static final String nombreArchivoRegionesCiudades="C:\\Users\\Abraham\\Desktop\\ciudades de Chile.txt";
	
	public ProcesamientoDatos(){
		cmsql= new ConexionMySQL("root", "speeddemon1");
		indice= new IndiceInvertido();
		neo4j= new ConexionNeo4j("neo4j", "speeddemon1");
		System.out.println("cantidad de documentos: "+String.valueOf(indice.cantidadDocumentos));
		nodos= new JsonArray();
		links= new JsonArray();
		crearOpinionesMetricasTodosCandidatos();
		//Se cierra la conexion a neo4j
		neo4j.cerrarConexion();
		//Una vez creadas todas las opiniones se escribe el archivo.json
		//escribirArchivoJson(nodos, links);
	}
	
	public static void main(String[] args){
		new ProcesamientoDatos();
		
	}
	
	//metodo que crea todas las opiniones de todos los candidatos en la base de datos mysql
	public void crearOpinionesMetricasTodosCandidatos(){
		//Se calcula la aprobacion de cada candidato por region
		//Para eso se cargan las regiones y cciudades de chile desde el archivo de texto
		//Que es el mismo que es usado para la geolocalizacion heuristica de los tweets
		String[] regiones;
		String[] ciudades;
		ArrayList<String[]> regionesCiudades=cargarRegionesCiudades(nombreArchivoRegionesCiudades);
		regiones= regionesCiudades.get(0);
		ciudades= regionesCiudades.get(1);
		//Se obtienen los ids de los candidatos en el sistema
		int[] idsCandidatos =cmsql.obtenerIdsCandidatos();
		//Para cada uno de los ids de los candidatos
		for(int i=0; i < idsCandidatos.length; i++){
			//Se crean las opiniones acerca del candidato segun su id en la base de datos mysql
			crearOpinionesCandidato(idsCandidatos[i]);
		}
		//double total=0;
		//una vez creadas las opiniones, se procede a calcular las aprobaciones y desaprobaciones de cada uno de los candidatos
		for(int i=0;i < idsCandidatos.length;i++){
			double aprob= calcularAprobacionNacional(idsCandidatos[i]);
			double desaprob=calcularDesaprobacionNacional(idsCandidatos[i]);
			//total=total+aprob;
			System.out.println("La aprobacion del candidato: "+idsCandidatos[i]+" es: "+aprob);
			System.out.println("La desaprobacion del candidato: "+idsCandidatos[i]+" es: "+desaprob);
			Date date= new Date();
	    	long miliseg= date.getTime();
	    	Timestamp fechaCreacionAprobacion = new Timestamp(miliseg);
	    	//Se deben insertar las aprobaciones nacionales de cada candidato en forma de metricas en la bd mysql
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
			//Se deben insertar las desaprobaciones nacionales de cada candidato en forma de metricas en la bd mysql
			int confirmacionDes = cmsql.insertarMetrica("Desaprobacion Nacional", desaprob, fechaCreacionAprobacion, idsCandidatos[i]);
			if(confirmacionDes==1){
				System.out.println("Se ha calculado con exito la desaprobacion nacional para el candidato: "+idsCandidatos[i]);
			}
			else if(confirmacionDes==2){
				System.out.println("Se calculo la desaprobacion para el candidato: "+idsCandidatos[i] +", pero no se pudo establecer la relacion");
			}
			else{
				System.out.println("Error en la insercion de la desaprobacion a la base de datos");
			}
			//se debe calcular la aprobacion y desaprobacion de un candidato en cada region
			for(int j=0; j < regiones.length; j++){
				double aprobacionRegional = calcularAprobacionRegional(idsCandidatos[i], regiones[j]);
				double desaprobacionRegional = calcularDesaprobacionRegional(idsCandidatos[i], regiones[j]);
				System.out.println("La aprobacion del candidato: "+idsCandidatos[i]+", en la region: "+regiones[j]+", es: "+aprobacionRegional);
				System.out.println("La desaprobacion del candidato: "+idsCandidatos[i]+", en la region: "+regiones[j]+", es: "+desaprobacionRegional);
				Date dateReg= new Date();
		    	long milisegReg= dateReg.getTime();
		    	Timestamp fechaCreacionAprobacionReg = new Timestamp(milisegReg);
		    	//Se deben insertar las aprobaciones regionales de cada candidato en forma de metricas en la bd mysql
				int confirmacionReg = cmsql.insertarMetrica("Aprobacion Region "+regiones[j], aprobacionRegional, fechaCreacionAprobacionReg, idsCandidatos[i]);
				if(confirmacionReg==1){
					System.out.println("Se ha calculado con exito la aprobacion para el candidato: "+idsCandidatos[i]+", en la region: "+regiones[j]);
				}
				else if(confirmacionReg==2){
					System.out.println("Se calculo la aprobacion para el candidato: "+idsCandidatos[i] +", en la region: "+regiones[j]+" pero no se pudo establecer la relacion");
				}
				else{
					System.out.println("Error en la insercion de la aprobacion regional a la base de datos");
				}
				//Se deben insertar las desaprobaciones regionales de cada candidato en forma de metricas en la bd mysql
				int confirmacionRegDes = cmsql.insertarMetrica("Desaprobacion Region "+regiones[j], desaprobacionRegional, fechaCreacionAprobacionReg, idsCandidatos[i]);
				if(confirmacionRegDes==1){
					System.out.println("Se ha calculado con exito la desaprobacion para el candidato: "+idsCandidatos[i]+", en la region: "+regiones[j]);
				}
				else if(confirmacionRegDes==2){
					System.out.println("Se calculo la desaprobacion para el candidato: "+idsCandidatos[i] +", en la region: "+regiones[j]+" pero no se pudo establecer la relacion");
				}
				else{
					System.out.println("Error en la insercion de la desaprobacion regional a la base de datos");
				}
			}
			
			//se debe calcular la aprobacion y desaprobacion de un candidato en cada ciudad
			for(int j=0; j < ciudades.length; j++){
				double aprobacionCiudad = calcularAprobacionCiudad(idsCandidatos[i], ciudades[j]);
				double desaprobacionCiudad = calcularDesaprobacionCiudad(idsCandidatos[i], ciudades[j]);
				System.out.println("La aprobacion del candidato: "+idsCandidatos[i]+", en la ciudad: "+ciudades[j]+", es: "+aprobacionCiudad);
				System.out.println("La desaprobacion del candidato: "+idsCandidatos[i]+", en la ciudad: "+ciudades[j]+", es: "+desaprobacionCiudad);
				Date dateCiudad= new Date();
		    	long milisegCiudad= dateCiudad.getTime();
		    	Timestamp fechaCreacionAprobacionCiudad = new Timestamp(milisegCiudad);
		    	//Se deben insertar las aprobaciones por ciudad de cada candidato en forma de metricas en la bd mysql
				int confirmacionCiudad = cmsql.insertarMetrica("Aprobacion Ciudad "+ciudades[j], aprobacionCiudad, fechaCreacionAprobacionCiudad, idsCandidatos[i]);
				if(confirmacionCiudad==1){
					System.out.println("Se ha calculado con exito la aprobacion para el candidato: "+idsCandidatos[i]+", en la ciudad: "+ciudades[j]);
				}
				else if(confirmacionCiudad==2){
					System.out.println("Se calculo la aprobacion para el candidato: "+idsCandidatos[i] +", en la ciudad: "+ciudades[j]+" pero no se pudo establecer la relacion");
				}
				else{
					System.out.println("Error en la insercion de la aprobacion por ciudad a la base de datos");
				}
				//Se deben insertar las desaprobaciones por ciudad de cada candidato en forma de metricas en la bd mysql
				int confirmacionCiudadDes = cmsql.insertarMetrica("Desaprobacion Ciudad "+ciudades[j], desaprobacionCiudad, fechaCreacionAprobacionCiudad, idsCandidatos[i]);
				if(confirmacionCiudadDes==1){
					System.out.println("Se ha calculado con exito la desaprobacion para el candidato: "+idsCandidatos[i]+", en la ciudad: "+ciudades[j]);
				}
				else if(confirmacionCiudadDes==2){
					System.out.println("Se calculo la desaprobacion para el candidato: "+idsCandidatos[i] +", en la ciudad: "+ciudades[j]+" pero no se pudo establecer la relacion");
				}
				else{
					System.out.println("Error en la insercion de la desaprobacion por ciudad a la base de datos");
				}
			}
		}
		//System.out.println("total: "+total);
	}
	
	
	
	
	
	//metodo que inserta todas las "opiniones" sobre un candidato a la base de datos mysql
	//AL BUSCAR LOS TWEETS POR PALABRAS CLAVES, SE PUEDE ANALIZAR 2 VECES EL MISMO TWEET
	//Y COMO ALGUNAS VECES EL ANALISIS DEL SENTIMIENTO PUEDE SER RANDOM, YA QUE NO ES TOTALMENTE
	//EFECTIVO, SE DEBEN VALIDAR LAS OPINIONES ANTES DE INSERTARLAS A MYSQL
	//LO MISMO PARA LAS RELACIONES EN NEO4J
	//LAS VALIDACIONES DEBEN REGIRSE POR LA IDEA DE QUE ES IMPOSIBLE
	//QUE UN USUARIO ESCRIBA 2 TWEETS EN LA MISMA FECHA (TIMESTAMP)
	//POR LO QUE LA COMBINACION AUTOR DEL TWEET-FECHA DE CREACION DEL TWEET ES UNICA
	//PARA EL CASO DE LAS OPINIONES, SE DEBE CONSULTAR SI EXISTE EXACTAMENTE EL MISMO REGISTRO 
	//YA QUE PUEDE SER QUE EL UNICO PARAMETRO QUE CAMBIE SEA EL DEL ID DEL CANDIDATO EN LA OPINION
	//PUESTO QUE EN UN MISMO TWEET O OPINION SE PUEDE HABLAR DE M�S DE 1 CANDIDATO
	//DEBIDO AL FACTOR DE AZAR EN EL ANALISIS DE SENTIMIENTO, ES IMPOSIBLE BORRAR LA INFO DE SOLO UNA
	//BASE DE DATOS AL INICIAR EL DEPURADOR, SE DEBE HACER LO MISMO TANTO PARA MYSQL COMO PARA NEO4J
	//EN ESTE CASO, SE USAN VALIDACIONES EN LAS INSERCIONES PARA QUE NO SE DUPLIQUEN LOS DATOS
	//Y DE ESTAS FORMA, SE MANTENGA LA COHERENCIA DE LOS DATOS ENTRE LAS DOS BD
	public void crearOpinionesCandidato(int idCandidato){
		String nombreCandidato = cmsql.obtenerNombreCandidato(idCandidato);
		//agregarNodoJson(nombreCandidato,idCandidato,true);
		//Se obtiene la cuenta oficial de twitter del candidato con ese id
		String cuentaCandidato = cmsql.obtenerCuentaCandidato(idCandidato);
		neo4j.insertarNodoCandidato(nombreCandidato, cuentaCandidato);
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
			//Se consulta por la keyword dentro del texto de un tweeet
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
						    		//si el autor del tweet opinion menciona al candidato, se agrega
						    		// la relacion de la mencion a la base de datos neo4j
						    		//Se crea el nodo Usuario, el nodo Candidato ya existe y se crea la relacion de que 
						    		//el usuario menciona al candidato
						    		neo4j.insertarNodoUsuario(autor, Integer.parseInt(opinion.get("user_followers_count")));
						    		//String fech = fechaCreacion.toString();
						    		neo4j.insertarRelacionMencionNodos(autor, cuentaCandidato, sentimiento, fechaCreacion.toString());
						    		//hay que ver si el candidato respondio a la mencion, para eso hay
						    		//que buscar en todos los tweets que ha realizado el candidato
						    		//y ver si el in_reply_tostatus_id es igual al id de este tweet opinion
						    		Document[] tweetsCandidato = indice.buscarDocumentosPorCampo("user_screen_name", cuentaCandidato,indice.cantidadDocumentos);
						    		long idTweetOpinion= Long.parseLong(opinion.get("id"));
						    		for(int n=0; n < tweetsCandidato.length; n++){
						    			//System.out.println("DOCUMENTOS ESCRITOS POR EL CANDIDATO: "+cuentaCandidato+": "+tweetsCandidato.length);
						    			long inReplyToStatusId= Long.parseLong(tweetsCandidato[n].get("in_reply_to_status_id"));
						    			if(inReplyToStatusId==idTweetOpinion){
						    				//Si son iguales, es que el candidato respondio al tweet opinion
						    				respuestaCandidato=true;
						    				Timestamp fechaRespuesta = Timestamp.valueOf(tweetsCandidato[n].get("created_at"));
						    				int sentimientoRespuesta = analizarSentimiento(tweetsCandidato[n].get("text"));
						    				if(sentimientoRespuesta!= -1){
						    					//Se agrega la relacion de que el nodo candidato respondio al nodo usuario
							    				neo4j.insertarRelacionRespuestaNodos(autor, cuentaCandidato, sentimientoRespuesta, fechaRespuesta.toString());
						    				}
						    				
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
		ArrayList<String> coincidencias = new ArrayList<String>();
		try{
			BufferedReader br  = new BufferedReader(
				    new InputStreamReader(new FileInputStream(archivo),"UTF-8"));
			String linea = br.readLine();
			boolean espacios=false;
			String palabraComparacion=null;
			while(linea!=null){
				palabraComparacion=null;
				espacios=false;
				//se debe armar la palabra sin los guiones bajos para encontrarla dentro del texto
				//campos contiene: en la primera posicion la palabra o termino en cuestio con guiones( si es que tiene)
				//y en la segunda si es positive o negative
				String[] campos = linea.split(" ");
				//Si la palabra o termino tiene al menos un guin bajo
				if(campos[0].indexOf("_")!=-1){
					//Se reemplazan por espacios
					palabraComparacion = campos[0].replace("_", " ");
					espacios=true;
				}
				//sino
				else{
					//la palabra es igual a la que se leyo del archivo
					palabraComparacion=campos[0];
				}
				if(espacios==false){
					//Si la palabra no tiene espacios, se separa el texto para comparar palabra
					//a palabra
					String[] palabrasTexto= text.split(" ");
					for(int i=0; i < palabrasTexto.length; i++){
						//System.out.println(palabrasTexto[i]);
						//Se valida que la palabra del texto no sea una mencion (que comience con @)
						//en esta implementacion se considera que la palabra no contenga el @
						//a futuro hay que implementar que la palabra comience con @, ya que
						//puede no contar palabr@s asi en el analisis del sentimiento
						if(palabrasTexto[i].equals(palabraComparacion)&& coincidencias.indexOf(palabraComparacion)==-1 && palabrasTexto[i].indexOf("@")==-1){
							System.out.println("Se encontro la palabra: "+palabraComparacion+" en el texto");
							coincidencias.add(palabraComparacion);
							if(campos[1].equals("positive")){
								cantidadPalabrasPositivas=cantidadPalabrasPositivas+1;
							}
							else{
								//Si no, es negative y es 0
								cantidadPalabrasNegativas=cantidadPalabrasNegativas+1;
							}
						}
					}
				}
				else{
					if(text.indexOf(palabraComparacion)!=-1 && coincidencias.indexOf(palabraComparacion)==-1){
						coincidencias.add(palabraComparacion);
						// 1 es positive
						System.out.println("Se encontro la palabra: "+palabraComparacion+" en el texto");
						if(campos[1].equals("positive")){
							cantidadPalabrasPositivas=cantidadPalabrasPositivas+1;
						}
						else{
							//Si no, es negative y es 0
							cantidadPalabrasNegativas=cantidadPalabrasNegativas+1;
						}
					}
				}
				linea=br.readLine();
			}
			/*System.out.println("Para texto: "+text);
			System.out.println("palabras positivas: "+cantidadPalabrasPositivas);
			System.out.println("palabras negativas: "+cantidadPalabrasNegativas);*/
			//Una vez revisadas todas las palabras del archivo
			//Si las cantidades de las palabras son 0, significa que no contenia ninguna de las palabras
			//del lexico, por lo que se asigna un valor de sentimiento al azar entre 0 y 2
			if(cantidadPalabrasPositivas==0 && cantidadPalabrasNegativas==0){
					int random = (int) Math.floor(Math.random()*(2-0+1)+0);  // Valor entre 0 y 2, ambos incluidos.
					br.close();
					//return random;
					return random;
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
				//si son iguales las cantidades, se considera como un sentimiento neutro = 2 
				else{
					//int random = (int) Math.floor(Math.random()*(1-0+1)+0);  // Valor entre 0 y 1, ambos incluidos.
					br.close();
					return 2;
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
	
	/*
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
	*/
	public ArrayList<String[]> cargarRegionesCiudades(String nombreArchivo){
		File archivo = new File(nombreArchivo);
		ArrayList<String[]>regionesCiudades=null;
		try {
			BufferedReader br  = new BufferedReader(
				    new InputStreamReader(new FileInputStream(archivo),"UTF-8"));
			String linea = br.readLine();
			ArrayList<String> auxRegiones = new ArrayList<String>();
			ArrayList<String> auxCiudades = new ArrayList<String>();
			linea = br.readLine();
			while(linea!=null){
				String[] campos = linea.split(",");
				if(!campos[0].equals("null") && !campos[1].equals("null")){
					if(auxRegiones.indexOf(campos[0])== -1){
						auxRegiones.add(campos[0]);	
					}
					auxCiudades.add(campos[1]);
				}
				linea=br.readLine();
			}
			br.close();
			regionesCiudades= new ArrayList<String[]>();
			String[] regiones= new String[auxRegiones.size()];
			String[] ciudades= new String[auxCiudades.size()];
			for(int i=0; i < regiones.length; i++){
				regiones[i]=auxRegiones.get(i);
			}
			for(int i=0; i < ciudades.length; i++){
				ciudades[i]=auxCiudades.get(i);
			}
			regionesCiudades.add(0,regiones);
			regionesCiudades.add(1,ciudades);
			return regionesCiudades;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regionesCiudades;
	}
	
	//metodo que calcula la aprobacion regional de un candidato
	//la formula es: aprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1
	//donde 
	/* tpos_total = total de tweets positivos entre todos los candidatos en una region
		tpos[i] = tpos_candidato[i]/tpos_total

	likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos en una region
	likes[i] = likes_candidato[i]/likes_total

	rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos en una region
	rts[i] = rts_candidato[i]/rts_total
	 */
	public double calcularAprobacionRegional(int idCandidato, String region){
		double aprobacion = 0;
		double cte1= 0.7;
		double cte2 = 0.2;
		double cte3= 0.1;
		double opPosTotal = (double) cmsql.obtenerCantidadOpinionesPositivas(region);
		//System.out.println(opPosTotal);
		double opPosCand = (double) cmsql.obtenerCantidadOpinionesPositivasCand(idCandidato, region);
		//System.out.println(opPosCand);
		double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesPositivas(region);
		//System.out.println(opPosLikesTotal);
		double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesPositivasCand(idCandidato, region);
		//System.out.println(opPosLikesCand);
		double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesPositivas(region);
		//System.out.println(opPosRtsTotal);
		double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesPositivasCand(idCandidato, region);
		//System.out.println(opPosRtsCand);
		double opinionesPosCand=0;
		double likesPosCand=0;
		double rtsPosCand=0;
		if(opPosTotal!=0){
			 opinionesPosCand = opPosCand/opPosTotal;	
		}
		if(opPosLikesTotal!=0){
			likesPosCand= opPosLikesCand/opPosLikesTotal;
		}
		if(opPosRtsTotal!=0){
			rtsPosCand = opPosRtsCand/opPosRtsTotal;;
		}
		//System.out.println("opniones: "+opinionesPosCand);
		//System.out.println("likes: "+likesPosCand);
		//System.out.println("rts: "+rtsPosCand);
		aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
		return aprobacion;
	}
	
	
	
	//metodo que calcula la aprobacion en una ciudad de un candidato
		//la formula es: aprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1
		//donde 
		/* tpos_total = total de tweets positivos entre todos los candidatos en una ciudad
			tpos[i] = tpos_candidato[i]/tpos_total

		likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos en una ciudad
		likes[i] = likes_candidato[i]/likes_total

		rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos en una ciudad
		rts[i] = rts_candidato[i]/rts_total
		 */
		public double calcularAprobacionCiudad(int idCandidato, String ciudad){
			double aprobacion = 0;
			double cte1= 0.7;
			double cte2 = 0.2;
			double cte3= 0.1;
			double opPosTotal = (double) cmsql.obtenerCantidadOpinionesPositivasCiudad(ciudad);
			//System.out.println(opPosTotal);
			double opPosCand = (double) cmsql.obtenerCantidadOpinionesPositivasCandCiudad(idCandidato, ciudad);
			//System.out.println(opPosCand);
			double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesPositivasCiudad(ciudad);
			//System.out.println(opPosLikesTotal);
			double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesPositivasCandCiudad(idCandidato, ciudad);
			//System.out.println(opPosLikesCand);
			double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesPositivasCiudad(ciudad);
			//System.out.println(opPosRtsTotal);
			double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesPositivasCandCiudad(idCandidato, ciudad);
			double opinionesPosCand=0;
			double likesPosCand=0;
			double rtsPosCand=0;
			if(opPosTotal!=0){
				 opinionesPosCand = opPosCand/opPosTotal;	
			}
			if(opPosLikesTotal!=0){
				likesPosCand= opPosLikesCand/opPosLikesTotal;
			}
			if(opPosRtsTotal!=0){
				rtsPosCand = opPosRtsCand/opPosRtsTotal;;
			}
			aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
			return aprobacion;
		}
		
		
		//CALCULO DE LA DESAPROBACION!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		//todo lo positivo se considera negativo en las consultas mysql
		
		
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
		public double calcularDesaprobacionNacional(int idCandidato){
			double aprobacion = 0;
			double cte1= 0.7;
			double cte2 = 0.2;
			double cte3= 0.1;
			double opPosTotal = (double) cmsql.obtenerCantidadOpinionesNegativas();
			System.out.println(opPosTotal);
			double opPosCand = (double) cmsql.obtenerCantidadOpinionesNegativasCand(idCandidato);
			System.out.println(opPosCand);
			double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesNegativas();
			System.out.println(opPosLikesTotal);
			double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesNegativasCand(idCandidato);
			System.out.println(opPosLikesCand);
			double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesNegativas();
			System.out.println(opPosRtsTotal);
			double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesNegativasCand(idCandidato);
			System.out.println(opPosRtsCand);
			double opinionesPosCand=0;
			double likesPosCand=0;
			double rtsPosCand=0;
			if(opPosTotal!=0){
				 opinionesPosCand = opPosCand/opPosTotal;	
			}
			if(opPosLikesTotal!=0){
				likesPosCand= opPosLikesCand/opPosLikesTotal;
			}
			if(opPosRtsTotal!=0){
				rtsPosCand = opPosRtsCand/opPosRtsTotal;;
			}
			aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
			return aprobacion;
		}

		
		//metodo que calcula la desaprobacion regional de un candidato
		//la formula es: desaprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1
		//donde 
		/* tpos_total = total de tweets positivos entre todos los candidatos en una region
			tpos[i] = tpos_candidato[i]/tpos_total

		likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos en una region
		likes[i] = likes_candidato[i]/likes_total

		rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos en una region
		rts[i] = rts_candidato[i]/rts_total
		 */
		public double calcularDesaprobacionRegional(int idCandidato, String region){
			double aprobacion = 0;
			double cte1= 0.7;
			double cte2 = 0.2;
			double cte3= 0.1;
			double opPosTotal = (double) cmsql.obtenerCantidadOpinionesNegativas(region);
			//System.out.println(opPosTotal);
			double opPosCand = (double) cmsql.obtenerCantidadOpinionesNegativasCand(idCandidato, region);
			//System.out.println(opPosCand);
			double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesNegativas(region);
			//System.out.println(opPosLikesTotal);
			double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesNegativasCand(idCandidato, region);
			//System.out.println(opPosLikesCand);
			double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesNegativas(region);
			//System.out.println(opPosRtsTotal);
			double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesNegativasCand(idCandidato, region);
			//System.out.println(opPosRtsCand);
			double opinionesPosCand=0;
			double likesPosCand=0;
			double rtsPosCand=0;
			if(opPosTotal!=0){
				 opinionesPosCand = opPosCand/opPosTotal;	
			}
			if(opPosLikesTotal!=0){
				likesPosCand= opPosLikesCand/opPosLikesTotal;
			}
			if(opPosRtsTotal!=0){
				rtsPosCand = opPosRtsCand/opPosRtsTotal;;
			}
			//System.out.println("rts: "+rtsPosCand);
			aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
			return aprobacion;
		}
		
		
		
		//metodo que calcula la aprobacion en una ciudad de un candidato
			//la formula es: aprobacion = tpos[i]*0.7 + likes[i]*0.2 + rts[i]*0.1
			//donde 
			/* tpos_total = total de tweets positivos entre todos los candidatos en una ciudad
				tpos[i] = tpos_candidato[i]/tpos_total

			likes_total = cantidad total de likes a los tweets POSITIVOS entre todos los candidatos en una ciudad
			likes[i] = likes_candidato[i]/likes_total

			rts_total = total de rts a los tweets POSITIVOS entre todos los candidatos en una ciudad
			rts[i] = rts_candidato[i]/rts_total
			 */
			public double calcularDesaprobacionCiudad(int idCandidato, String ciudad){
				double aprobacion = 0;
				double cte1= 0.7;
				double cte2 = 0.2;
				double cte3= 0.1;
				double opPosTotal = (double) cmsql.obtenerCantidadOpinionesNegativasCiudad(ciudad);
				//System.out.println(opPosTotal);
				double opPosCand = (double) cmsql.obtenerCantidadOpinionesNegativasCandCiudad(idCandidato, ciudad);
				//System.out.println(opPosCand);
				double opPosLikesTotal = (double) cmsql.obtenerCantidadLikesOpinionesNegativasCiudad(ciudad);
				//System.out.println(opPosLikesTotal);
				double opPosLikesCand = (double) cmsql.obtenerCantidadLikesOpinionesNegativasCandCiudad(idCandidato, ciudad);
				//System.out.println(opPosLikesCand);
				double opPosRtsTotal = (double) cmsql.obtenerCantidadRtsOpinionesNegativasCiudad(ciudad);
				//System.out.println(opPosRtsTotal);
				double opPosRtsCand = (double) cmsql.obtenerCantidadRtsOpinionesNegativasCandCiudad(idCandidato, ciudad);
				//System.out.println(opPosRtsCand);
				double opinionesPosCand=0;
				double likesPosCand=0;
				double rtsPosCand=0;
				if(opPosTotal!=0){
					 opinionesPosCand = opPosCand/opPosTotal;	
				}
				if(opPosLikesTotal!=0){
					likesPosCand= opPosLikesCand/opPosLikesTotal;
				}
				if(opPosRtsTotal!=0){
					rtsPosCand = opPosRtsCand/opPosRtsTotal;;
				}
				//System.out.println("rts: "+rtsPosCand);
				aprobacion = (opinionesPosCand * cte1) +(likesPosCand*cte2) +(rtsPosCand*cte3);
				return aprobacion;
			}
}
