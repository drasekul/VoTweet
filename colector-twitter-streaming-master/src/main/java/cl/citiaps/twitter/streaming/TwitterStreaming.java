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
import MySQLConnection.MySQLConnector;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.SiteStreamsListener;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;

public class TwitterStreaming {

	private final TwitterStream twitterStream;
	private Set<String> keywords;
	private static DB db = null;
	private static MongoClient mongo = null;
	private static DBCollection tweets = null;
	private static GeoApiContext context =null;
	private static BufferedReader br =	null;
	private static String url= "https://api.twitter.com/1.1/users/show.json?screen_name=";
			
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
				almacenarTweet(status);
			}
		};
		/*
		SiteStreamsListener siteListener = new SiteStreamsListener(){
			@Override
			public void onStatus(long forUser, Status status){
				almacenarTweet(status);
			}

			@Override
			public void onDeletionNotice(long forUser, StatusDeletionNotice statusDeletionNotice) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFriendList(long forUser, long[] friendIds) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFavorite(long forUser, User source, User target, Status favoritedStatus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUnfavorite(long forUser, User source, User target, Status unfavoritedStatus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFollow(long forUser, User source, User followedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUnfollow(long forUser, User source, User unfollowedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDirectMessage(long forUser, DirectMessage directMessage) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeletionNotice(long forUser, long directMessageId, long userId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListMemberAddition(long forUser, User addedMember, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListMemberDeletion(long forUser, User deletedMember, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListSubscription(long forUser, User subscriber, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListUnsubscription(long forUser, User subscriber, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListCreation(long forUser, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListUpdate(long forUser, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListDeletion(long forUser, User listOwner, UserList list) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserProfileUpdate(long forUser, User updatedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserSuspension(long forUser, long suspendedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserDeletion(long forUser, long deletedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onBlock(long forUser, User source, User blockedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUnblock(long forUser, User source, User unblockedUser) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRetweetedRetweet(User source, User target, Status retweetedStatus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFavoritedRetweet(User source, User target, Status favoritedStatus) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDisconnectionNotice(String line) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onException(Exception ex) {
				// TODO Auto-generated method stub
				
			}
			
		};
		*/

		FilterQuery fq = new FilterQuery();
		fq.track(keywords.toArray(new String[0]));
		this.twitterStream.addListener(listener);
		this.twitterStream.filter(fq);
		/*this.twitterStream.addListener(siteListener);
		this.twitterStream.site(true, ids);
		*/
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		MongoDbConnector mdbc= new MongoDbConnector();
		mongo= mdbc.crearConexion();
		MySQLConnector mysqlc = new MySQLConnector("root","speeddemon1");
		if(mongo!=null){
			//Si la bd no existe, la crea automaticamente
			db = mongo.getDB("votweet");
			//Se crea, si no existe, la tabla "tweet" en la base de datos votweet
			tweets = db.getCollection("tweet");
			mysqlc.cargarKeywords();
			ArrayList<String> cuentasOficiales = mysqlc.obtenerCuentasOficiales();
			//long[] ids = obtenerIdsCandidatos(url, cuentasOficiales);
			/*long[] ids = new long[cuentasOficiales.size()];
			ids[0]=13623532;
			ids[1]=185748219;
			ids[2]=16193496;
			ids[3]=1248064796;
			ids[4]=14050989;
			ids[5]=55273142;
			ids[6]=337542243;
			ids[7]=123955962;
			ids[8]=18927356;
			ids[9]=31438919;
			//ids[10]=2931485195 ;
			//ids[11]=765266725029998592;
			ids[12]=480770126;
			ids[13]=19003727;
			ids[14]=38550531;
			*/
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
	/*
	private static long[] obtenerIdsCandidatos(String url, ArrayList<String> nombresUsuariosCandidatos){
		long[] ids= new long[nombresUsuariosCandidatos.size()];
		for(int i=0; i < nombresUsuariosCandidatos.size(); i++){
			try {
				URL urlx = new URL(url+nombresUsuariosCandidatos.get(i));
				HttpURLConnection urlConnection = (HttpURLConnection) urlx.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				Reader reader = new InputStreamReader(in, "UTF-8");
				JsonParser parser = new JsonParser();
				JsonElement jsonElement = parser.parse(reader);
				JsonObject json = jsonElement.getAsJsonObject();
				ids[i]=json.get("id").getAsLong();
				System.out.println("candidato: "+nombresUsuariosCandidatos.get(i) +", id: "+ids[i]);
	        } catch (IOException e) {
	           e.printStackTrace();
	        }
		}
		return ids;
		}
	*/
	private void almacenarTweet(Status status){
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
		documento.put("is_retweeted", status.isRetweeted());
		documento.put("created_at", status.getCreatedAt().toString());
		documento.put("is_retweet", status.isRetweet());
		if(status.isRetweet()){
			Status rt= status.getRetweetedStatus();
			documento.put("rt_id", rt.getId());
			documento.put("rt_retweet_count",rt.getRetweetCount());
			documento.put("rt_fav_count", rt.getFavoriteCount());
		}
		else{
			documento.put("rt_id",-1);
			documento.put("rt_retweet_count",-1);
			documento.put("rt_fav_count",-1);
		}
		documento.put("in_reply_to_user_screen_name",status.getInReplyToScreenName());
		documento.put("in_reply_to_user_id", status.getInReplyToUserId());
		documento.put("withheld_in_countries",status.getWithheldInCountries());
		documento.put("is_truncated", status.isTruncated());
		documento.put("is_possibly_sensitive", status.isPossiblySensitive());
		documento.put("lang", status.getLang());
		User user = status.getUser();
		documento.put("user_id", user.getId());
		documento.put("user_screen_name", user.getScreenName());
		documento.put("user_name", user.getName());
		if(user.getLocation()!=null){
			documento.put("user_location", user.getLocation().toString());
		}
		else{
			documento.put("user_location", "null");
		}
		documento.put("user_followers_count", user.getFollowersCount());
		documento.put("user_fav_count", user.getFavouritesCount());
		UserMentionEntity[] ume = status.getUserMentionEntities();
		if(ume!=null){
			String menciones="";
			for(int i=0; i < ume.length; i++){
				if(i==ume.length-1){
					menciones=menciones+ume[i].getScreenName();
				}
				else{
					menciones=menciones+ume[i].getScreenName()+",";
				}
			}
			documento.put("others_users_mentions",menciones);
		}
		else{
			documento.put("others_users_mentions", "null");
		}
		if(status.getPlace()!=null){
			if(status.getPlace().getFullName()!=null){
				int nacional =0;
				ArrayList<ArrayList<String>> listaCiudadesChile= leerArchivo();
				 for (int i=0; i < listaCiudadesChile.size(); i++){
				 	for(int j=0; j < listaCiudadesChile.get(i).size(); j++){
				 		if(status.getPlace().getFullName().indexOf(listaCiudadesChile.get(i).get(j)) != -1){
				 			//System.out.println("region" + listaCiudadesChile.get(i).get(0));
				 			//System.out.println("ciudad" + listaCiudadesChile.get(i).get(1));
				 			documento.put("country_location", "Chile");
				 			documento.put("region_location", listaCiudadesChile.get(i).get(0));
				 			documento.put("city_location",listaCiudadesChile.get(i).get(1));
				 			nacional=1;
				 		}
				 	}
				 }
				 if(nacional==0 && (status.getPlace().getFullName().indexOf("Chile")!= -1 || status.getPlace().getFullName().indexOf("CHILE")!= -1 || status.getPlace().getFullName().indexOf("chile")!= -1)){
					 documento.put("country_location", "Chile");
					 documento.put("region_location","null");
					 documento.put("citi_location", "null");
					 nacional=1;
				 }
				 //Si nacional es 0, significa que el tweet no es nacional
				 else if(nacional==0){
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
		else{
			String location = user.getLocation();
			if(location != null){
				//documento.put("user_location",location.toString());
				System.out.println("user_location: "+location.toString());
				int nacional=0;
				ArrayList<ArrayList<String>> listaCiudadesChile= leerArchivo();
				 for (int i=0; i < listaCiudadesChile.size(); i++){
				 	for(int j=0; j < listaCiudadesChile.get(i).size(); j++){
				 		//System.out.println("Chequeando en: " +listaCiudadesChile.get(i).get(j));
				 		if(location.indexOf(listaCiudadesChile.get(i).get(j)) != -1){
				 			System.out.println("region" + listaCiudadesChile.get(i).get(0));
				 			System.out.println("ciudad" + listaCiudadesChile.get(i).get(1));
				 			documento.put("country_location", "Chile");
				 			documento.put("region_location", listaCiudadesChile.get(i).get(0));
				 			documento.put("city_location",listaCiudadesChile.get(i).get(1));
				 			nacional=1;
				 		}
				 	}
				 }
				 if(nacional==0 &&(location.indexOf("Chile")!=-1 || location.indexOf("CHILE")!=-1 || location.indexOf("chile")!=-1)){
					 documento.put("country_location", "Chile");
					 documento.put("region_location", "null");
					 documento.put("city_location", "null");
					 nacional=1;
				 }
				 //Si nacional es 0, significa que el tweet no es nacional
				 else if(nacional==0){
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
	}
