import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LegacyIntField;
import org.apache.lucene.document.LegacyLongField;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class IndiceInvertido {
	private Directory indexDirectory;
	private ConexionMongoDB cmdb;
	public int cantidadDocumentos;
	
	public IndiceInvertido(){
		crearIndice();
	}
	
	public void crearIndice(){
		cmdb = new ConexionMongoDB();
		cantidadDocumentos=0;
		DBCursor cursor = cmdb.tweets.find();
		try {
			indexDirectory = FSDirectory.open(Paths.get("indice/"));
			SpanishAnalyzer analyzer= new SpanishAnalyzer();
			IndexWriterConfig config= new IndexWriterConfig(analyzer);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(indexDirectory,config);
			DBObject tweet = cursor.next();
			while(cursor.hasNext()){
				long id = (long) tweet.get("id");
				int fav_count = (int) tweet.get("fav_count");
				int retweet_count = (int) tweet.get("retweet_count");
				String text = tweet.get("text").toString();
				String country_location =tweet.get("country_location").toString();
				/*System.out.println("tweet: "+contador);
				System.out.println(text);
				contador++;
				*/
				String region_location = tweet.get("region_location").toString();
				String city_location = tweet.get("city_location").toString();
				String created_at = tweet.get("created_at").toString();
				String is_retweeted = tweet.get("is_retweeted").toString();
				/*String is_retweet = cursor.next().get("is_retweet").toString();
				long rt_id = (long) cursor.next().get("rt_id");
				int rt_retweet_count= (int) cursor.next().get("rt_retweet_count");
				int rt_fav_count= (int) cursor.next().get("rt_fav_count");
				*/
				String in_reply_to_user_screen_name;
				try{
					in_reply_to_user_screen_name=tweet.get("in_reply_to_user_screen_name").toString();
				}catch(NullPointerException e){
					in_reply_to_user_screen_name ="none";
				}
				long in_reply_to_user_id = (long) tweet.get("in_reply_to_user_id");
				long in_reply_to_status_id = (long) tweet.get("in_reply_to_status_id");
				long user_id = (long) tweet.get("user_id");
				String user_screen_name = tweet.get("user_screen_name").toString();
				String user_name = tweet.get("user_name").toString();
				String others_users_mentions;
				try{
					others_users_mentions = tweet.get("others_users_mentions").toString();
				}catch(NullPointerException e){
					others_users_mentions="none";
				}
				Document documento = crearDocumento(id,fav_count,retweet_count,country_location,region_location,city_location,text,created_at,is_retweeted,in_reply_to_user_screen_name,in_reply_to_user_id,user_id,user_screen_name,user_name,others_users_mentions,in_reply_to_status_id);
				try {
					//if(writer.getConfig().getOpenMode()== OpenMode.CREATE){
						writer.addDocument(documento);
						cantidadDocumentos++;
					//}
					/*else{
						writer.updateDocument(new Term("retweet_count"))
					}
					*/
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				tweet=cursor.next();
			}
			System.out.println("Indice creado");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		

	}
	
	private Document crearDocumento(long id, int fav_count, int retweet_count, String country_location, String region_location, String city_location, String text, String createdAt,String is_retweeted,String in_reply_to_user_screen_name,long in_reply_to_user_id, long user_id, String user_screen_name, String user_name, String others_users_mentions, long in_reply_to_status_id){
		Document documento= new Document();
		documento.add(new LongPoint("id",id));
		documento.add(new StoredField("id", id));
		documento.add(new IntPoint("fav_count",fav_count));
		documento.add(new StoredField("fav_count", fav_count));
		documento.add(new IntPoint("retweet_count",retweet_count));
		documento.add(new StoredField("retweet_count", retweet_count));
		documento.add(new TextField("text", text, Field.Store.YES));
		/*documento.add(new StoredField("is_retweet", is_retweet));
		documento.add(new LongPoint("rt_id", rt_id));
		documento.add(new IntPoint("rt_retweet_count", rt_retweet_count));
		documento.add(new IntPoint("rt_fav_count", rt_fav_count));*/
		documento.add(new StoredField("is_retweeted", is_retweeted));
		documento.add(new StringField("country_location",country_location, Field.Store.YES));
		documento.add(new StringField("region_location", region_location,Field.Store.YES));
		documento.add(new StringField("city_location", city_location,Field.Store.YES));
		//System.out.println("city: "+city_location);
		//System.out.println("menciones: "+others_users_mentions);
		documento.add(new StringField("created_at", createdAt,Field.Store.YES));
		documento.add(new StringField("in_reply_to_user_screen_name", in_reply_to_user_screen_name,Field.Store.YES));
		documento.add(new LongPoint("in_reply_to_user_id", in_reply_to_user_id));
		documento.add(new StoredField("in_reply_to_user_id", in_reply_to_user_id));
		documento.add(new LongPoint("in_reply_to_status_id",in_reply_to_status_id));
		documento.add(new StoredField("in_reply_to_status_id", in_reply_to_status_id));
		documento.add(new LongPoint("user_id",user_id));
		documento.add(new StoredField("user_id",user_id));
		documento.add(new StringField("user_screen_name",user_screen_name,Field.Store.YES));
		documento.add(new StringField("user_name",user_name,Field.Store.YES));
		documento.add(new TextField("others_users_mentions", others_users_mentions,Field.Store.YES));
		/*System.out.println("dentro del documento: ");
		System.out.println("id: "+documento.get("id"));
		System.out.println("text: "+documento.get("text"));
		System.out.println("rt_count: "+documento.get("retweet_count"));
		System.out.println("fav_count: "+documento.get("fav_count"));
		System.out.println("is_rtd: "+documento.get("is_retweeted"));
		System.out.println("pais: "+documento.get("country_location"));
		System.out.println("region: "+documento.get("region_location"));
		System.out.println("ciudad: "+documento.get("city_location"));
		System.out.println("created_at: "+documento.get("created_at"));
		System.out.println("in_reply_to_user_screen_name: "+documento.get("in_reply_to_user_screen_name"));
		System.out.println("in_reply_to_user_id: "+documento.get("in_reply_to_user_id"));
		System.out.println("in_reply_to_status_id: "+ documento.get("in_reply_to_status_id"));
		System.out.println("user_id: "+ documento.get("user_id"));
		System.out.println("user_screen_name: "+documento.get("user_screen_name"));
		System.out.println("user_name: "+documento.get("user_name"));
		System.out.println("others_users_mentions: "+documento.get("others_users_mentions"));*/
		return documento;
	}
	
	/*
	public Document[] consultarTermino(String campo, String valorCampo, int cantidadDocumentos){
		Document[] resultados =null;
		try{
			IndexReader reader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TermQuery query = new TermQuery(new Term(campo,valorCampo));
			TopDocs results=searcher.search(query, cantidadDocumentos);
			ScoreDoc [] hits = results.scoreDocs;
			resultados = new Document[hits.length]; 
			for(int i=0; i < hits.length; i++){
				resultados[i]=searcher.doc(hits[i].doc);
			}
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return resultados;
		
	}
	*/
	
	/*
	public ScoreDoc[] consultarLong(String campo, long valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			Query query = LongPoint.newExactQuery(campo, valorCampo);
			TopDocs primera=searcher.search(query, 1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			hits[0]=primera.scoreDocs[0];
			for(int i=1; i < hits.length; i++){
				hits[i]=resultados.scoreDocs[i];
			}
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	*/
	
	/*
	public ScoreDoc[] consultarInt(String campo, int valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			Query query = IntPoint.newExactQuery(campo, valorCampo);
			TopDocs primera=searcher.search(query,1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			hits[0]=primera.scoreDocs[0];
			for(int i=1; i < hits.length; i++){
				hits[i]=resultados.scoreDocs[i];
			}
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	*/
	
	//Metodo que devuelve un arreglo de documentos luego de buscar o consultar por un campo
	//con valor que tiene espacio
	public Document[] buscarDocumentosPorCampo(String campo, String valorCampo, int cantidadDocumentos){
		Document[] resultados =null;
		try{
			//Se lee el indice creado
	        IndexReader reader = DirectoryReader.open(indexDirectory);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        SpanishAnalyzer analyzer = new SpanishAnalyzer();
	        QueryParser parser = new QueryParser(campo, analyzer);
	        Query query = parser.parse(valorCampo);
			TopDocs results=searcher.search(query, cantidadDocumentos);
			ScoreDoc [] hits = results.scoreDocs;
			resultados = new Document[hits.length]; 
			//System.out.println("Dentro de la busqueda: ");
			for(int i=0; i < hits.length; i++){
				resultados[i]=searcher.doc(hits[i].doc);
				//System.out.println(resultados[i].get("text"));
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return resultados;
		
	}
		
	
}
