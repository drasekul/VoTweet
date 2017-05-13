import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class ConexionMongoDB {
	public  MongoClient cliente=null;
	public  DB baseDatos=null;
	public  DBCollection tweets=null;
	
	public ConexionMongoDB(){
		this.cliente = crearConexion();
		try{
			this.baseDatos = cliente.getDB("votweet");
			this.tweets = baseDatos.getCollection("tweet");
		}
		catch(Exception e){
			System.out.println("Error en la conexion a MongoDB");
		}
	}
	
	 public static MongoClient crearConexion() {
	    	MongoClient mongo = null;
	        try {
	            mongo = new MongoClient("localhost", 27017);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return mongo;
	    }
}
