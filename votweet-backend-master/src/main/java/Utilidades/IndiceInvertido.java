package Utilidades;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndiceInvertido {
	private Directory indexDirectory;
	private static final String rutaIndice = "C:\\Users\\Abraham\\workspace\\depurador-master\\indice";
	
	public IndiceInvertido(){
		try {
			indexDirectory = FSDirectory.open(Paths.get(rutaIndice));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Metodo que devuelve un arreglo de documentos luego de buscar o consultar por un campo
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
