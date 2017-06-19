package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.CandidatoMetricaFacade;
import facade.MetricaFacade;
import model.CandidatoMetrica;
import model.Metrica;


@Path("/metricasCandidatos")
public class CandidatoMetricaService {
	
	private static final String nombreArchivoSimbolosNumerosRegiones = "C:\\Users\\Abraham\\Desktop\\numeros regiones Chile.txt";
	//Lista que contiene los pares simboloNumeroRegion, nombreRegion
	ArrayList<String[]> numerosRegiones = cargarSimbolosNumerosRegiones(nombreArchivoSimbolosNumerosRegiones);
	
	@EJB 
	CandidatoMetricaFacade candidatoMetricaFacadeEJB;
	@EJB 
	MetricaFacade metricaFacadeEJB;
	
	Logger logger = Logger.getLogger(CandidatoMetricaService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<CandidatoMetrica> findAll(){
		return candidatoMetricaFacadeEJB.findAll();
	}
	
	/*
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public CandidatoMetrica find(@PathParam("id") Integer id) {
        return candidatoMetricaFacadeEJB.find(id);
    }
    */
	
	//Metodo que retorna todas las aprobaciones y desaprobaciones nacional de un candidato a lo largo del tiempo
	//EL json debe contener fecha, aprobacion y desaprobacion
	@GET
	@Path("{idCandidato}/aprobacionNacional")
	public String obtenerDatosHistoricosNacionalesCandidato(@PathParam("idCandidato") Integer idCandidato){
		String respuesta=null;
		ArrayList<String[]> fechasAprob = new ArrayList<String[]>();
		ArrayList<String[]> fechasDesaprob = new ArrayList<String[]>();
		JsonArrayBuilder jsonArrayBuilder =Json.createArrayBuilder();
		List<CandidatoMetrica> candidatosMetricas = candidatoMetricaFacadeEJB.findAll();
		List<Metrica> metricas = metricaFacadeEJB.findAll();
		for(int i=0; i < candidatosMetricas.size(); i++){
			if(candidatosMetricas.get(i).getCdtoId()==idCandidato){
				//Si se cumple esto, candidatoMetrica.get(i) contiene el id de la metrica que esta relacionada
				//con el candidato
				int idMetrica = candidatosMetricas.get(i).getMetId();
				//Hay que devolver todas las aprobaciones y desaprobaciones nacionales del candidayo 
				//en el sistema
				for(int j=0; j < metricas.size(); j++){
					if(metricas.get(j).getMetId()==idMetrica){
						//En este punto, metricas.get(j) es una de todas las metricas del candidato
						//Hay que ver si el nombre contiene la palabra Nacional
						if(metricas.get(j).getMetNombre().indexOf("Nacional") != -1){
							//SI la metrica coontiene la palabra Nacional en el nombre, sirve
							//Hay que ver si es aprobacion o desaprobacion
							if(metricas.get(j).getMetNombre().indexOf("Aprobacion")!= -1){
								//SI se cumple, es aprobacion
								String[] fechaAprob = new String[2];
								fechaAprob[0]= metricas.get(j).getMetFecha().toString();
								fechaAprob[1]= String.valueOf(metricas.get(j).getMetValor()*100);
								fechasAprob.add(fechaAprob);
							}
							else if(metricas.get(j).getMetNombre().indexOf("Desaprobacion") != -1){
								//SI se cumple, es desaprobacion
								String[] fechaDesaprob = new String[2];
								fechaDesaprob[0]= metricas.get(j).getMetFecha().toString();
								fechaDesaprob[1]= String.valueOf(metricas.get(j).getMetValor()*100);
								fechasDesaprob.add(fechaDesaprob);
							}
						}
					}
				}
			}
		}
		//EN este punto, en fechasAprob estan todos los pares fechas-aprobacion de un candidato 
		//Y en fechasDesaprob, estan todos los pares fechas-desaprobacion de un candidato
		//Hay que unir ahora en fecha-aprobacion-desaprobacion, comparando
		//si la aprobacion y desaprobacion tienen la misma fecha
		//Se va comparando una par de fecha-aprob con todos los pares de fecha-desaprob
		for(int i=0; i < fechasAprob.size(); i++){
			String [] fechaAprob = fechasAprob.get(i);
			for(int j=0; j < fechasDesaprob.size(); j++){
				String [] fechaDesaprob = fechasDesaprob.get(j);
				if(fechaAprob[0].equals(fechaDesaprob[0])){
					//Si las fechas son iguales, se crea el objeto json 
					//con la fecha, aprobacion y desaprobacion y se agrega al array de json
					JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
					//Se corta la fecha para que quede solo en año/mes/dia
					//String[] fechaHora = fechaAprob[0].split(" ");
					jsonObjectBuilder.add("fecha", fechaAprob[0]);
					jsonObjectBuilder.add("aprobacion", Double.parseDouble(fechaAprob[1]));
					jsonObjectBuilder.add("desaprobacion", Double.parseDouble(fechaDesaprob[1]));
					JsonObject jsonObject = jsonObjectBuilder.build();
					jsonArrayBuilder.add(jsonObject);
				}
			}
		}
		JsonArray jsonArray =jsonArrayBuilder.build();
		respuesta = jsonArray.toString();
		return respuesta;
	}
	
	
	
	//Metodo que retorna todas las aprobaciones y desaprobaciones nacional de un candidato a lo largo del tiempo
	//EL json debe contener fecha, aprobacion y desaprobacion
	@GET
	@Path("{idCandidato}/aprobacionRegionalPromedio")
	public String obtenerDatosRegionalesPromedioCandidato(@PathParam("idCandidato") Integer idCandidato){
		String respuesta=null;
		ArrayList<String[]> regionesAprob = new ArrayList<String[]>();
		ArrayList<String[]> regionesDesaprob = new ArrayList<String[]>();
		JsonArrayBuilder jsonArrayBuilder =Json.createArrayBuilder();
		List<CandidatoMetrica> candidatosMetricas = candidatoMetricaFacadeEJB.findAll();
		List<Metrica> metricas = metricaFacadeEJB.findAll();
		for(int i=0; i < candidatosMetricas.size(); i++){
			if(candidatosMetricas.get(i).getCdtoId()==idCandidato){
				//Si se cumple esto, candidatoMetrica.get(i) contiene el id de la metrica que esta relacionada
				//con el candidato
				int idMetrica = candidatosMetricas.get(i).getMetId();
				//Hay que devolver todas las aprobaciones y desaprobaciones nacionales del candidayo 
				//en el sistema
				for(int j=0; j < metricas.size(); j++){
					if(metricas.get(j).getMetId()==idMetrica){
						//En este punto, metricas.get(j) es una de todas las metricas del candidato
						//Hay que ver que region esta en el nombre de la apropbacion
						for(int k=0; k < numerosRegiones.size(); k++){
							String[] numeroRegion = numerosRegiones.get(k);
							String region = numeroRegion[1];
							if(metricas.get(j).getMetNombre().indexOf(region) != -1){
								//SI la metrica coontiene el nombre de la region en el nombre, se debe asociar a esa region
								//Hay que ver si es aprobacion o desaprobacion
								if(metricas.get(j).getMetNombre().indexOf("Aprobacion")!= -1){
									//SI se cumple, es aprobacion
									String[] regionAprob = new String[2];
									regionAprob[0]= region;
									regionAprob[1]= String.valueOf(metricas.get(j).getMetValor()*100);
									regionesAprob.add(regionAprob);
								}
								else if(metricas.get(j).getMetNombre().indexOf("Desaprobacion") != -1){
									//SI se cumple, es desaprobacion
									String[] regionDesaprob = new String[2];
									regionDesaprob[0]=region;
									regionDesaprob[1]= String.valueOf(metricas.get(j).getMetValor()*100);
									regionesDesaprob.add(regionDesaprob);
								}
							}
						}
						
					}
				}
			}
		}
		//EN este punto, en regionesAprob estan todas las tuplas regiones-aprobacion de un candidato 
		//Y en regionesDesaprob, estan todas las tuplas regiones-desaprobacion de un candidato
		//Hay que crear un objeto json por cada region, que debe contener
		//identificadorRegion = simbolo romano del numero de la region
		//nombreRegion = identificador + de + nombre de la region
		//aprobacionPromedio= es el promedio de todas las aprobaciones de la region
		//desaprobacionPromedio= es el promedio de todas las desaprobaciones de la region
		//Para cada region
		for(int i=0; i < numerosRegiones.size();i++){
			int contador=0;
			double acumulador=0;
			double aprobacionPromedioRegion=0;
			double desaprobacionPromedioRegion=0;
			String reg = numerosRegiones.get(i)[1];
			String regSinTilde = numerosRegiones.get(i)[2];
			String numero = numerosRegiones.get(i)[0];
			//Se buscan todas las aprobaciones de esa region
			for(int j=0; j < regionesAprob.size(); j++){
				//Si la region de ese par region-aprobacion coincide con la que se esta
				//analizando
				if(regionesAprob.get(j)[0].equals(reg)){
					//Se acumula la aprobacion
					acumulador=acumulador + Double.parseDouble(regionesAprob.get(j)[1]);
					//se cuenta la aprobacion
					contador++;
				}
			}
			//una vez sumadas todas las aprobaciones de la region, se calcula el promedio de estas
			aprobacionPromedioRegion= redondear(acumulador/contador,3);
			
			//Luego, se obtiene la desaprobacion promedio
			acumulador=0;
			contador=0;
			//Se buscan todas las aprobaciones de esa region
			for(int j=0; j < regionesDesaprob.size(); j++){
				//Si la region de ese par region-desaprobacion coincide con la que se esta
				//analizando
				if(regionesDesaprob.get(j)[0].equals(reg)){
					//Se acumula la desaprobacion
					acumulador=acumulador + Double.parseDouble(regionesDesaprob.get(j)[1]);
					//se cuenta la desaprobacion
					contador++;
				}
			}
			//Una vez sumadas todas las desaprobaciones de la region, se calcula el promedio de estas
			desaprobacionPromedioRegion=redondear(acumulador/contador,3);
			//Se crea el objeto json
			JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
			jsonObjectBuilder.add("Identificador", numero);
			jsonObjectBuilder.add("Nombre", numero +" de "+regSinTilde);
			jsonObjectBuilder.add("aprobacion",aprobacionPromedioRegion);
			jsonObjectBuilder.add("desaprobacion",desaprobacionPromedioRegion);
			JsonObject jsonObject = jsonObjectBuilder.build();
			//Se agrega el objeto json al jsonArray
			jsonArrayBuilder.add(jsonObject);
		}
		//se crea el json array
		JsonArray jsonArray = jsonArrayBuilder.build();
		respuesta= jsonArray.toString();
		return respuesta;
	}
	
	//Metodo que carga en una lista de arreglos los pares simboloNumeroRegion, nombreRegion del pais de Chile
	public ArrayList<String[]> cargarSimbolosNumerosRegiones( String nombreArchivo){
		File archivo = new File(nombreArchivo);
		ArrayList<String[]>numerosRegiones=new ArrayList<String[]>();
		try {
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			linea = br.readLine();
			while(linea!=null){
				String[] campos = linea.split(",");
				numerosRegiones.add(campos);
				linea=br.readLine();
			}
			br.close();
			return numerosRegiones;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numerosRegiones;
	}
	
	public static double redondear(double numero,int digitos)
	{
	      int cifras=(int) Math.pow(10,digitos);
	      return Math.rint(numero*cifras)/cifras;
	}
	

}
