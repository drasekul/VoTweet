package Utilidades;

import java.util.ArrayList;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;


public class Neo4j {
	public static Driver driver;
	public static Session session;
	
	//EN el constructor se hace la conexion al servidor neo4j
	public Neo4j(String user, String pass){
		driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic(user, pass) );
		session = driver.session();
	}
	
	public void cerrarConexion(){
		session.close();
		driver.close();
	}
	
	//metodo que retorna los datos de las relaciones de mencion de un usuario
	//a un candidato, siendo estas relaciones con los 20 usuarios con mas seguidores
	//retorna una lista de arreglos de string, que es nula si no existen relaciones de tipo mencion para el candidato
	//en la que cada arreglo contiene las tuplas: 
	//screenNameUser - followersUser - sentimientoMencion - fechaMencion
	public ArrayList<String[]> obtenerDatosRelacionesMencion(String screenNameCandidato){
		ArrayList<String[]> datosRelaciones =null;
		String consulta ="match (u:Usuario)-[r:MENCIONA]->(c:Candidato) where c.screenName='"+screenNameCandidato+"' return u.followers as followersUser, u.screenName as screenNameUser, r.sentimiento as sentimiento, r.fecha as fecha ORDER BY u.followers DESC LIMIT 20";
		StatementResult result = session.run(consulta);
		if(result.hasNext()){
			datosRelaciones= new ArrayList<String[]>();
		}
		while(result.hasNext()){
			Record record = result.next();
			String screenNameUser = record.get("screenNameUser").asString();
			String followersUser = String.valueOf(record.get("followersUser").asInt());
			String sentimientoMencion = record.get("sentimiento").asString();
			String fechaMencion = record.get("fecha").asString();
			String[] datosRelacion = new String[4];
			datosRelacion[0] = screenNameUser;
			datosRelacion[1] = followersUser;
			datosRelacion[2] = sentimientoMencion;
			datosRelacion[3] = fechaMencion;
			datosRelaciones.add(datosRelacion);
		}
		return datosRelaciones;
	}
	
	//metodo que retorna los datos de las relaciones de respuesta de un candidato
		//a un usuario, siendo estas relaciones con los 20 usuarios con mas seguidores
		//retorna una lista de arreglos de string que es nula si no existen relaciones de tipo respuesta para el candidato
		//en la que cada arreglo contiene las tuplas: 
		//screenNameUser - followersUser - sentimientoMencion - fechaMencion
		public ArrayList<String[]> obtenerDatosRelacionesRespuesta(String screenNameCandidato){
			ArrayList<String[]> datosRelaciones =null;
			String consulta ="match (c:Candidato)-[r:RESPONDE]->(u:Usuario) where c.screenName='"+screenNameCandidato+"' return u.followers as followersUser, u.screenName as screenNameUser, r.sentimiento as sentimiento, r.fecha as fecha ORDER BY u.followers DESC LIMIT 20";
			StatementResult result = session.run(consulta);
			if(result.hasNext()){
				datosRelaciones= new ArrayList<String[]>();
			}
			while(result.hasNext()){
				Record record = result.next();
				String screenNameUser = record.get("screenNameUser").asString();
				String followersUser = String.valueOf(record.get("followersUser").asInt());
				String sentimientoMencion = record.get("sentimiento").asString();
				String fechaMencion = record.get("fecha").asString();
				String[] datosRelacion = new String[4];
				datosRelacion[0] = screenNameUser;
				datosRelacion[1] = followersUser;
				datosRelacion[2] = sentimientoMencion;
				datosRelacion[3] = fechaMencion;
				datosRelaciones.add(datosRelacion);
			}
			return datosRelaciones;
		}
}
