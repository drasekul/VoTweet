import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;

public class ProcesamientoDatos {
	private ConexionMySQL cmsql;
	private IndiceInvertido indice;
	
	public ProcesamientoDatos(){
		cmsql= new ConexionMySQL("root", "speeddemon1");
		indice= new IndiceInvertido();
	}
	
	public static void main(String[] args){
		new ProcesamientoDatos();
		
	}
	public double obtenerOpinion(String keyword){
		ScoreDoc[] resultados;
		if(keyword.indexOf("")!=-1){
			resultados=indice.consultarFrase("text", keyword);
		}
		else{
			resultados=indice.consultarTermino("text",keyword);
		}
		for(int i=0; i < resultados.length; i++){
			Document opinion;
			try {
				opinion = indice.searcher.doc(resultados[i].doc);
				//Si es RT 
				if(opinion.get("is_retweet").equals("true")){
					//Se busca el documento que contiene el tweet que este retuitea
					ScoreDoc[] res = indice.consultarLong("rt_id",Long.parseLong(opinion.get("rt_id")));
					Document op = indice.searcher.doc(res[0].doc);
					//Y se inserta la "nueva" opinion en la base de datos mysql si es que se encontro el twett retuiteado
					if(op!=null){
						
					}
					
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
}
