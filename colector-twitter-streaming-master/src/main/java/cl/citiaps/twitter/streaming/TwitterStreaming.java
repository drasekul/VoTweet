package cl.citiaps.twitter.streaming;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import MongoDBConnection.MongoDbConnector;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserMentionEntity;

public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	private static DB db = null;
	private static MongoClient mongo = null;
	private static DBCollection tweets = null;
	private static GeoApiContext context =null;
	private static BufferedReader br =	null;
	private String url= "https://api.twitter.com/1.1/users/show.json?screen_name=";
			
	private TwitterStreaming() {
		this.twitterStream = new TwitterStreamFactory().getInstance();
		this.keywords = new HashSet<>();
		loadKeywords();
	}

	private void loadKeywords() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		StatusListener listener = new StatusListener() {

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {

			}

			@Override
			public void onStatus(Status status) {
				//Cuando se recibe un tweet, se guarda en la Bd Mongo
				File archivo= new File("C:\\Users\\Abraham\\Desktop\\ciudades de Chile.txt");
				try{
					FileReader fr = new FileReader (archivo);
					br = new BufferedReader(fr);
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
				BasicDBObject documento = new BasicDBObject();
				documento.put("id", status.getId());
				documento.put("text", status.getText());
				System.out.println(status.getText());
				documento.put("fav_count", status.getFavoriteCount());
				documento.put("retweet_count", status.getRetweetCount());
				documento.put("created_at", status.getCreatedAt().toString());
				UserMentionEntity[] ume = status.getUserMentionEntities();
				if(ume!=null){
					documento.put("mentions", ume.toString());
				}
				if(status.getPlace()!=null){
					int nacional =0;
					ArrayList<ArrayList<String>> listaCiudadesChile= leerArchivo();
					 for (int i=0; i < listaCiudadesChile.size(); i++){
					 	for(int j=1; j < listaCiudadesChile.get(i).size(); j++){
					 		if(status.getPlace().getFullName().indexOf(listaCiudadesChile.get(i).get(j)) != -1 && (status.getPlace().getFullName().indexOf("Chile")!= -1)){
					 			//System.out.println("region" + listaCiudadesChile.get(i).get(0));
					 			//System.out.println("ciudad" + listaCiudadesChile.get(i).get(1));
					 			documento.put("country_location", "Chile");
					 			documento.put("region_location", listaCiudadesChile.get(i).get(0));
					 			documento.put("city_location",listaCiudadesChile.get(i).get(1));
					 			nacional=1;
					 		}
					 	}
					 }
					 //Si nacional es 0, significa que el tweet no es nacional
					 if(nacional==0){
						documento.put("country_location", "Extranjero");
				 		documento.put("region_location", "Extranjero");
				 		documento.put("city_location","Extranjero");
					 }
				}
				else{
					User user = status.getUser();
					documento.put("user",user.toString());
					String location = user.getLocation();
					if(location != null){
						int nacional=0;
						ArrayList<ArrayList<String>> listaCiudadesChile= leerArchivo();
						 for (int i=0; i < listaCiudadesChile.size(); i++){
						 	for(int j=1; j < listaCiudadesChile.get(i).size(); j++){
						 		if(location.indexOf(listaCiudadesChile.get(i).get(j)) != -1 && location.indexOf("Chile")!=-1){
						 			//System.out.println("region" + listaCiudadesChile.get(i).get(0));
						 			//System.out.println("ciudad" + listaCiudadesChile.get(i).get(1));
						 			documento.put("country_location", "Chile");
						 			documento.put("region_location", listaCiudadesChile.get(i).get(0));
						 			documento.put("city_location",listaCiudadesChile.get(i).get(1));
						 			nacional=1;
						 		}
						 	}
						 }
						 //Si nacional es 0, significa que el tweet no es nacional
						 if(nacional==0){
							documento.put("country_location", "Extranjero");
					 		documento.put("region_location", "Extranjero");
					 		documento.put("city_location","Extranjero");
						 }
					}
					else{
						documento.put("country_location", "null");
						documento.put("region_location", "null");
						documento.put("city_location", "null");
						//System.out.println("sin localizacion");
					}
				}
				tweets.insert(documento);
			}
		};

		FilterQuery fq = new FilterQuery();

		fq.track(keywords.toArray(new String[0]));

		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		MongoDbConnector mdbc= new MongoDbConnector();
		mongo= mdbc.crearConexion();
		if(mongo!=null){
			//Si la bd no existe, la crea automaticamente
			db = mongo.getDB("votweet");
			//Se crea, si no existe, la tabla "tweet" en la base de datos votweet
			tweets = db.getCollection("tweet");
			long[] ids = obtenerIdsCandidatos(url, );
			//context = new GeoApiContext().setApiKey("AIzaSyDRu-xoKOmS8bSJqAaWfme4E1tzmMFqXjA");
			new TwitterStreaming().init();
		}
		else{
			System.out.println("Error en la conexión a MongoDB\n");
		}
	}
	
	private ArrayList<ArrayList<String>> leerArchivo(){
		ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();
		try {
			br.readLine();
			String linea = br.readLine();
			while(linea!=null){
				ArrayList<String> subLista = new ArrayList<String>();
				String[] campos = linea.split(",");
				for(int i=0; i < campos.length ; i++){
					subLista.add(campos[i]);
				}
				lista.add(subLista);
				linea=br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
		
	}
	
	private long[] obtenerIdsCandidatos(String url, String[] nombresUsuariosCandidatos){
		long[] ids= new long[nombresUsuariosCandidatos.length];
		for(int i=0; i < nombresUsuariosCandidatos.length; i++){
			try {
				URL urlx = new URL(url+nombresUsuariosCandidatos);
				HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				Reader reader = new InputStreamReader(in, "UTF-8");
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(reader);
				JsonObject json = jsonElement.getAsJsonObject();
				ids[i]=json.get("id").getAsLong();
				System.out.println("candidato: "+nombresUsuariosCandidatos[i] +", id: "+ids[i]);
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }

		}
		return ids;
	}

}
