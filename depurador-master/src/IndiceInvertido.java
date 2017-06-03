import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.IntPoint;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import com.mongodb.DBCursor;

public class IndiceInvertido {
	private Directory indexDirectory;
	private ConexionMongoDB cmdb;
	public IndexSearcher searcher;
	private IndexWriter writer;
	private IndexReader reader;
	
	public IndiceInvertido(){
		crearIndice();
	}
	
	public void crearIndice(){
		cmdb = new ConexionMongoDB();
		DBCursor cursor = cmdb.tweets.find();
		try {
			indexDirectory = FSDirectory.open(Paths.get("indice/"));
			StandardAnalyzer analyzer= new StandardAnalyzer();
			IndexWriterConfig iwc= new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(indexDirectory,iwc);
			while(cursor.hasNext()){
				long id = (long) cursor.next().get("id");
				int fav_count = (int) cursor.next().get("fav_count");
				int retweet_count = (int) cursor.next().get("retweet_count");
				String text = cursor.next().get("text").toString();
				String country_location =cursor.next().get("country_location").toString();
				String region_location = cursor.next().get("region_location").toString();
				String city_location = cursor.next().get("city_location").toString();
				String created_at = cursor.next().get("created_at").toString();
				String is_retweeted = cursor.next().get("is_retweeted").toString();
				String is_retweet = cursor.next().get("is_retweet").toString();
				long rt_id = (long) cursor.next().get("rt_id");
				int rt_retweet_count= (int) cursor.next().get("rt_retweet_count");
				int rt_fav_count= (int) cursor.next().get("rt_fav_count");
				String in_reply_to_user_screen_name = cursor.next().get("in_reply_to_user_screen_name").toString();
				long in_reply_to_user_id = (long) cursor.next().get("in_reply_to_user_id");
				long user_id = (long) cursor.next().get("user_id");
				String user_screen_name = cursor.next().get("user_screen_name").toString();
				String user_name = cursor.next().get("user_name").toString();
				String other_users_mentions = cursor.next().get("other_users_mentions").toString();
				Document documento = crearDocumento(writer,id,fav_count,retweet_count,country_location,region_location,city_location,text,created_at,is_retweet,is_retweeted,in_reply_to_user_screen_name,in_reply_to_user_id,user_id,user_screen_name,user_name,other_users_mentions,rt_id,rt_retweet_count,rt_fav_count);
				try {
					writer.addDocument(documento);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
		

	}
	
	private Document crearDocumento(IndexWriter writer, long id, int fav_count, int retweet_count, String country_location, String region_location, String city_location, String text, String createdAt, String is_retweet, String is_retweeted,String in_reply_to_user_screen_name,long in_reply_to_user_id, long user_id, String user_screen_name, String user_name, String others_users_mentions, long rt_id, int rt_retweet_count, int rt_fav_count){
		Document documento= new Document();
		documento.add(new LongPoint("id", id));
		documento.add(new IntPoint("fav_count", fav_count));
		documento.add(new IntPoint("retweet_count", retweet_count));
		documento.add(new TextField("text", text, Field.Store.YES));
		documento.add(new StoredField("is_retweet", is_retweet));
		documento.add(new LongPoint("rt_id", rt_id));
		documento.add(new IntPoint("rt_retweet_count", rt_retweet_count));
		documento.add(new IntPoint("rt_fav_count", rt_fav_count));
		documento.add(new StoredField("is_retweeted", is_retweeted));
		documento.add(new StringField("country_location",country_location, Field.Store.YES));
		documento.add(new StringField("region_location", region_location, Field.Store.YES));
		documento.add(new StringField("city_location", city_location, Field.Store.YES));
		documento.add(new TextField("created_at", createdAt, Field.Store.YES));
		documento.add(new StringField("in_reply_to_user_screen_name", in_reply_to_user_screen_name, Field.Store.YES));
		documento.add(new LongPoint("in_reply_to_user_id", in_reply_to_user_id));
		documento.add(new LongPoint("user_id",user_id));
		documento.add(new StringField("user_screen_name",user_screen_name, Field.Store.YES));
		documento.add(new StringField("user_name",user_name, Field.Store.YES));
		documento.add(new TextField("others_users_mentions", others_users_mentions, Field.Store.YES));
		
		return documento;
	}
	
	public ScoreDoc[] consultarTermino(String campo, String valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			TermQuery query = new TermQuery(new Term(campo,valorCampo));
			TopDocs primera=searcher.search(query, 1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	
	public ScoreDoc[] consultarLong(String campo, long valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			Query query = LongPoint.newExactQuery(campo, valorCampo);
			TopDocs primera=searcher.search(query, 1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	
	public ScoreDoc[] consultarInt(String campo, int valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			Query query = IntPoint.newExactQuery(campo, valorCampo);
			TopDocs primera=searcher.search(query, 1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	
	public ScoreDoc[] consultarFrase(String campo, String valorCampo){
		ScoreDoc[] hits =null;
		try{
			reader = DirectoryReader.open(indexDirectory);
			searcher = new IndexSearcher(reader);
			PhraseQuery query = new PhraseQuery(campo,valorCampo);
			TopDocs primera=searcher.search(query, 1);
			TopDocs resultados=searcher.searchAfter(primera.scoreDocs[0],query, primera.totalHits);
			hits = new ScoreDoc[1 + resultados.scoreDocs.length];
			//Se deben entregar los campos como numeros o string segun corresponda
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		return hits;
		
	}
	
	
}
