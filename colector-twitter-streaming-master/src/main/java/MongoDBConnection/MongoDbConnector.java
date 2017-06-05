package MongoDBConnection;

import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongoDbConnector {
	/**
     * Clase para crear una conexión a MongoDB.
     * @return MongoClient conexión
     */
	
	public MongoDbConnector(){
		
	}
	
    public MongoClient crearConexion() {
    	MongoClient mongo = null;
        try {
        	mongo = new MongoClient("localhost",27017);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return mongo;
    }
}
