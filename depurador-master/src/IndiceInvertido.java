import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import com.mongodb.DBCursor;

public class IndiceInvertido {
	private Directory indexDirectory;
	private ConexionMongoDB cmdb;
	
	public IndiceInvertido(){
		crearIndice();
	}
	
	public void crearIndice(){
		cmdb = new ConexionMongoDB();
		DBCursor cursor = cmdb.tweets.find();
		try {
			indexDirectory = FSDirectory.open(Paths.get("indice/"));
			Analyzer analyzer= new StandardAnalyzer();
			IndexWriterConfig iwc= new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(indexDirectory,iwc);
			while(cursor.hasNext()){
				long id = (long) cursor.next().get("id");
				int fav_count = (int) cursor.next().get("fav_count");
				int retweet_count = (int) cursor.next().get("retweet_count");
				String text = (String) cursor.next().get("text");
				String country_location = (String) cursor.next().get("country_location");
				String region_location = (String) cursor.next().get("region_location");
				String city_location = (String) cursor.next().get("city_location");
				String created_at = (String) cursor.next().get("created_at");
				Document documento = crearDocumento(writer,id,fav_count,retweet_count,country_location,region_location,city_location,text,created_at);
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
	
	private Document crearDocumento(IndexWriter writer, long id, int fav_count, int retweet_count, String country_location, String region_location, String city_location, String text, String createdAt){
		Document documento= new Document();
		documento.add(new LongPoint("id", id));
		documento.add(new IntPoint("fav_count", fav_count));
		documento.add(new IntPoint("retweet_count", fav_count));
		documento.add(new TextField("text", text, Field.Store.YES));
		documento.add(new TextField("country_location",country_location, Field.Store.YES));
		documento.add(new TextField("region_location", region_location, Field.Store.YES));
		documento.add(new TextField("city_location", city_location, Field.Store.YES));
		documento.add(new TextField("created_at", createdAt, Field.Store.YES));
		return documento;
	}
	
	public ScoreDoc[] consultarTermino(String campo, String valorCampo){
		ScoreDoc[] hits =null;
		try{
			IndexReader reader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);
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
	
	public ScoreDoc[] consultarFrase(String campo, String valorCampo){
		ScoreDoc[] hits =null;
		try{
			IndexReader reader = DirectoryReader.open(indexDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);
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
