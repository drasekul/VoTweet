import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class ConexionNeo4j {
	public static Driver driver;
	public static Session session;
	
	//EN el constructor se hace la conexion al servidor neo4j
	public ConexionNeo4j(String user, String pass){
		driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic(user, pass) );
		session = driver.session();
		//eliminarDatosBD();
	}
	
	public void cerrarConexion(){
		session.close();
		driver.close();
	}
	
	//funcion que vee si existe un usuario en la base de datos neo4j
	public boolean buscarUsuario(String screenNameUser){
		String consulta = "match (u:Usuario) where u.screenName='"+screenNameUser+"' return u.screenName";
		StatementResult result = session.run(consulta);
		if(result.hasNext()){
			return true;
		}
		return false;
	}
	
	//funcion que vee si existe un candidato en la base de datos neo4j
		public boolean buscarCandidato(String screenNameCandidato){
			String consulta = "match (c:Candidato) where c.screenName='"+screenNameCandidato+"' return c.screenName";
			StatementResult result = session.run(consulta);
			if(result.hasNext()){
				return true;
			}
			return false;
		}
		
	//Funcion que inserta un nodo de candidato en la base de datos neo4j
	public boolean insertarNodoCandidato(String nameCandidato, String screenNameCandidato){
		//Se chequea si ya existe ese candidato
		boolean existeCandidato = buscarCandidato(screenNameCandidato);
		if(existeCandidato){
			return false;
		}
		else{
			String consulta = "create (c:Candidato {name:'"+nameCandidato+"', screenName:'"+screenNameCandidato+"'})";
			session.run(consulta);
			return true;
		}
	}	
	
	//funcion que inserta un nodo de usuario en la base de datos neo4j
	public boolean insertarNodoUsuario(String screenNameUser, int cantidadSeguidoresUser){
		boolean existeUsuario = buscarUsuario(screenNameUser);
		if(existeUsuario){
			return false;
		}
		else{
			String consulta = "create (u:Usuario {screenName:'"+screenNameUser+"', followers:"+cantidadSeguidoresUser+"})";
			session.run(consulta);
			return true;
		}
	}
	
	
	//funcion que verifica si existe una relacion de mencion entre un nodo usuario
	//y un nodo candidato
	//se considera si existe la relacion, si todos sus atributos ya existen, incluyendo los 
	//screenNames o nombre de usuario en twitter del candidato y del usuario
	public boolean verificarRelacionMencion(String screenNameUser, String screenNameCandidato, int sentimiento, String fechaRelacion){
		//Se obtienen todas las relaciones de mencion entre el usuario con ese nombre
		// y el candidato con ese nombre
		String consulta = "match (u:Usuario)-[r:MENCIONA]->(c:Candidato) where u.screenName='"+screenNameUser+"' and c.screenName='"+screenNameCandidato+"'return r.fecha as fecha, r.sentimiento as sentimiento";
		StatementResult result = session.run(consulta);
		//Se verifica la fecha y el sentimiento de cada una de esas relaciones
		while(result.hasNext()){
			Record record = result.next();
			String fecha = record.get("fecha").asString();
			//Si una de las relaciones tiene la misma fecha
			//entonces la relacion existe
			if(fecha.equals(fechaRelacion)){
				return true;
			}
		}
		//Si se llega hasta aca en la ejecucion
		//significa que no se tiene ningun resultado, por lo tanto no existe la relacion
		// en la bd
		return false;
	}
	
	//funcion que verifica si existe una relacion de respuesta entre un nodo candidato
		//y un nodo usuario
		//se considera si existe la relacion, si todos sus atributos ya existen, incluyendo los 
		//screenNames o nombre de usuario en twitter del candidato y del usuario
		public boolean verificarRelacionRespuesta(String screenNameUser, String screenNameCandidato, int sentimiento, String fechaRelacion){
			//Se obtienen todas las relaciones de respuesta entre el usuario con ese nombre
			// y el candidato con ese nombre
			String consulta = "match (c:Candidato)-[r:RESPUESTA]->(u:Usuario) where u.screenName='"+screenNameUser+"' and c.screenName='"+screenNameCandidato+"'return r.fecha as fecha, r.sentimiento as sentimiento";
			StatementResult result = session.run(consulta);
			//Se verifica la fecha y el sentimiento de cada una de esas relaciones
			while(result.hasNext()){
				Record record = result.next();
				String fecha = record.get("fecha").asString();
				//Si una de las relaciones tiene la misma fecha
				//entonces la relacion existe
				if(fecha.equals(fechaRelacion)){
					return true;
				}
			}
			//Si se llega hasta aca en la ejecucion
			//significa que no se tiene ningun resultado, por lo tanto no existe la relacion
			// en la bd
			return false;
		}
		
	//funcion que crea la relacion en la que un usuario menciona a un candidato en la base de datos neo4j
	//antes de llamar a esta funcion, se debe ejecutar si o si la insercion del usuario y del candidato
	//para asegurarse de que existan los nodos que conformaran la relacion
	public void insertarRelacionMencionNodos(String screenNameUser, String screenNameCandidato, int sentimiento, String fechaRelacion){
		//Se verifica si ya existe la relacion a insertar
		boolean existeRelacion = verificarRelacionMencion(screenNameUser, screenNameCandidato,sentimiento,fechaRelacion);
		//Solo si no existe se inserta
		if(existeRelacion==false){
			String consulta="match (u:Usuario) where u.screenName='"+screenNameUser+"' match (c:Candidato) where c.screenName='"+screenNameCandidato+"' with u,c create (u)-[:MENCIONA {sentimiento:'"+sentimiento+"', fecha:'"+fechaRelacion+"'}]->(c)";
			session.run(consulta);
		}
	}
	
	public void insertarRelacionRespuestaNodos(String screenNameUser, String screenNameCandidato, int sentimiento, String fechaRelacion){
		//Se verifica si ya existe la relacion a insertar
		boolean existeRelacion = verificarRelacionRespuesta(screenNameUser, screenNameCandidato, sentimiento, fechaRelacion);
		if(existeRelacion==false){
			String consulta="match (u:Usuario) where u.screenName='"+screenNameUser+"' match (c:Candidato) where c.screenName='"+screenNameCandidato+"' with u,c create (c)-[:RESPONDE {sentimiento:'"+sentimiento+"', fecha:'"+fechaRelacion+"'}]->(u)";
			session.run(consulta);
		}
	}
	
	//funcion que elimina todos los datos de la base de datos, se ejecuta cada vez que se ejecuta el depurador
	public void eliminarDatosBD(){
		String consulta = "match (a)-[r]->(b) delete r";
		session.run(consulta);
		consulta="match (n) delete n";
		session.run(consulta);
	}
}
