import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class ConexionNeo4j {
	public static Driver driver;
	public static Session session;
	
	//EN el constructor se hace la conexion al servidor neo4j
	public ConexionNeo4j(String user, String pass){
		driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic(user, pass) );
		session = driver.session();
	}
	
	public void cerrarConexion(){
		session.close();
		driver.close();
	}
	
	public void consulta(String consulta){
		session.run(consulta);
	}
}
