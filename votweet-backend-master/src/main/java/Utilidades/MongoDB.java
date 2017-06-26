package Utilidades;

import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDB {
	public  MongoClient cliente=null;
	public  DB baseDatos=null;
	public  DBCollection tweets=null;
	
	public MongoDB(){
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
	            mongo = new MongoClient("localhost",27017);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	        return mongo;
	    }
	 
}
