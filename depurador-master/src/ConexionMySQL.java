import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;



public class ConexionMySQL {
	public Connection con=null;
	public String user;
	public String pass;
	private static final String url="jdbc:mysql://localhost:3306/votweet";
	
	public ConexionMySQL(String userMySqlServer, String passMySqlServer){
		try{
			con=DriverManager.getConnection(url,userMySqlServer, passMySqlServer);
			this.user=userMySqlServer;
			this.pass=passMySqlServer;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
	
	public String obtenerCuentaCandidato(int idCandidato){
		String cuenta=null;
		ResultSet cuentaCandidato= consultar("SELECT c.cdto_cuenta_twitter FROM candidato c WHERE c.cdto_id="+String.valueOf(idCandidato));
		try{
			if(cuentaCandidato.next()){
				cuenta=cuentaCandidato.getString("cdto_cuenta_twitter");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return cuenta;
	}
	
	public String obtenerNombreCandidato(int idCandidato){
		String nombre=null;
		ResultSet nombreCandidato= consultar("SELECT c.cdto_nombre FROM candidato c WHERE c.cdto_id="+String.valueOf(idCandidato));
		try{
			if(nombreCandidato.next()){
				nombre=nombreCandidato.getString("cdto_nombre");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return nombre;
	}
	
	//Metodo que retorna todos los ids de los candidatos
	public int[] obtenerIdsCandidatos(){
		int[] ids = null;
		ArrayList<String> idsAux;
		ResultSet idsCandidatos = consultar("SELECT cdto_id FROM candidato");
		try{
			idsAux= new ArrayList<String>();
			while(idsCandidatos.next()){
				idsAux.add(String.valueOf(idsCandidatos.getInt("cdto_id")));
			}
			ids= new int[idsAux.size()];
			for(int i=0; i < idsAux.size(); i++){
				ids[i]=Integer.parseInt(idsAux.get(i));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		/*
		for(int i=0; i < ids.length; i++){
			System.out.println(ids[i]);
		}
		*/
		return ids;
	}
	
	
	
	//Metodo que retorna las keywords asociadas a un candidato en particular a partir del id de este
	public ArrayList<String> obtenerKeywordsCandidato(int idCandidato){
		ArrayList<String> res=null;
		ResultSet keywordsCandidato = consultar("SELECT k.kwd_texto FROM keyword k WHERE k.cdto_id="+idCandidato);
		try {
			res= new ArrayList<String>();
			while(keywordsCandidato.next()){
				res.add(keywordsCandidato.getString("kwd_texto"));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		for(int i=0; i < resultadoConsulta.length; i++){
			System.out.println(resultadoConsulta[i]);
		}
		*/
		return res;
		}
	
	public ResultSet consultar(String consulta){
		 ResultSet resultado;
	        try {
	            Statement sentencia = con.createStatement();
	            resultado = sentencia.executeQuery(consulta);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return null;
	        }        return resultado;
		
	}
	
	//Metodo que valida que no exista la opinion a insertar
	//retorna 0 si no existe la opinion
	//retorna 1 si existe
	//retorna 2 si se desea ingresar una actualizacion de una opinion
	//es decir, que hayan cambiado la cantidad de rts y likes
	public int verificarOpinion(int cantidadLikes, int cantidadRt, String autor, Timestamp fechaCreacion, int idCandidato, String pais, String region, String ciudad, int sentimiento, boolean mencionaCandidato, boolean respCandidato){
		try {
			PreparedStatement ps = con.prepareStatement("SELECT o.opinion_id FROM opinion o WHERE o.opinion_likes=? AND o.opinion_retweets=? AND o.opinion_autor=? AND o.opinion_fecha=? AND o.cdto_id=? AND o.opinion_pais_ubicacion=? AND o.opinion_region_ubicacion=? AND o.opinion_ciudad_ubicacion=? AND o.opinion_menciona_candidato=? AND o.opinion_resp_candidato=?");
			ps.setInt(1, cantidadLikes);
			ps.setInt(2, cantidadRt);
			ps.setString(3, autor);
			ps.setTimestamp(4, fechaCreacion);
			ps.setInt(5, idCandidato);
			ps.setString(6, pais);
			ps.setString(7, region);
			ps.setString(8, ciudad);
			ps.setBoolean(9, mencionaCandidato);
			ps.setBoolean(10, respCandidato);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				//Si llega el id, es que existe, por tanto se retorna 1
				return 1;
			}
			else{
				//Sino, se busca nuevamente, pero esta vez sin considerar rt y likes
				PreparedStatement ps1 = con.prepareStatement("SELECT o.opinion_id FROM opinion o WHERE o.opinion_autor=? AND o.opinion_fecha=? AND o.cdto_id=? AND o.opinion_pais_ubicacion=? AND o.opinion_region_ubicacion=? AND o.opinion_ciudad_ubicacion=? AND o.opinion_menciona_candidato=? AND o.opinion_resp_candidato=?");
				ps1.setString(1, autor);
				ps1.setTimestamp(2, fechaCreacion);
				ps1.setInt(3, idCandidato);
				ps1.setString(4, pais);
				ps1.setString(5, region);
				ps1.setString(6, ciudad);
				ps1.setBoolean(7, mencionaCandidato);
				ps1.setBoolean(8, respCandidato);
				ResultSet rs1 = ps1.executeQuery();
				//SI llega el id
				if(rs1.next()){
					//Se actualiza la cantidad de likes y de retweets de la opinion con ese id, ya que es imposible 
					//que una persona escriba 2 veces el mismo tweet en el mismo timestamp
					PreparedStatement ps2 = con.prepareStatement("UPDATE opinion SET opinion_retweets=?, opinion_likes=? WHERE opinion_id=?");
					ps2.setInt(1, cantidadRt);
					ps2.setInt(2, cantidadLikes);
					ps2.setInt(3, rs1.getInt("opinion_id"));
					ps2.executeUpdate();
					return 2;
				}
				//Si no llega el id, se vuelve a buscar ahora sin considerar los likes solamente
				else{
					PreparedStatement ps3 = con.prepareStatement("SELECT o.opinion_id FROM opinion o WHERE o.opinion_retweets=? AND o.opinion_autor=? AND o.opinion_fecha=? AND o.cdto_id=? AND o.opinion_pais_ubicacion=? AND o.opinion_region_ubicacion=? AND o.opinion_ciudad_ubicacion=? AND o.opinion_menciona_candidato=? AND o.opinion_resp_candidato=?");
					ps3.setInt(1, cantidadRt);
					ps3.setString(2, autor);
					ps3.setTimestamp(3, fechaCreacion);
					ps3.setInt(4, idCandidato);
					ps3.setString(5, pais);
					ps3.setString(6, region);
					ps3.setString(7, ciudad);
					ps3.setBoolean(8, mencionaCandidato);
					ps3.setBoolean(9, respCandidato);
					ResultSet rs2 = ps3.executeQuery();
					if(rs2.next()){
						//Si llega el id, se actualiza la cantidad de likes
						PreparedStatement ps4 = con.prepareStatement("UPDATE opinion SET opinion_likes=? WHERE opinion_id=?");
						ps4.setInt(1, cantidadLikes);
						ps4.setInt(2, rs1.getInt("opinion_id"));
						ps4.executeUpdate();
						return 2;
					}
					else{
						//Si no, se busca omitiendo la cantidad de likes
						PreparedStatement ps5 = con.prepareStatement("SELECT o.opinion_id FROM opinion o WHERE o.opinion_likes=? AND o.opinion_autor=? AND o.opinion_fecha=? AND o.cdto_id=? AND o.opinion_pais_ubicacion=? AND o.opinion_region_ubicacion=? AND o.opinion_ciudad_ubicacion=? AND o.opinion_menciona_candidato=? AND o.opinion_resp_candidato=?");
						ps5.setInt(1, cantidadLikes);
						ps5.setString(2, autor);
						ps5.setTimestamp(3, fechaCreacion);
						ps5.setInt(4, idCandidato);
						ps5.setString(5, pais);
						ps5.setString(6, region);
						ps5.setString(7, ciudad);
						ps5.setBoolean(8, mencionaCandidato);
						ps5.setBoolean(9, respCandidato);
						ResultSet rs3 = ps5.executeQuery();
						if(rs3.next()){
							//Si llega el id, se actualiza la cantidad de rts
							PreparedStatement ps4 = con.prepareStatement("UPDATE opinion SET opinion_retweets=? WHERE opinion_id=?");
							ps4.setInt(1, cantidadRt);
							ps4.setInt(2, rs1.getInt("opinion_id"));
							ps4.executeUpdate();
							return 2;
						}
						else{
							//Si se llega a este punto, significa que no esta la opinion
							return 0;
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	//Metodo que inserta una opinion en la base de datos
	public int insertarOpinion(int idCandidato,Timestamp fechaCreacion, int sentimiento,String pais, String region, String ciudad, int cantidadRetweets, int cantidadLikes,String autor, boolean mencionaCandidato, boolean respuestaCandidato){
		try {
			int verificacion = verificarOpinion(cantidadLikes, cantidadRetweets, autor, fechaCreacion, idCandidato, pais, region,ciudad, sentimiento, mencionaCandidato, respuestaCandidato);
			if(verificacion==1){
				System.out.println("Ya existe la opinion creada por: "+autor +" en: "+fechaCreacion+", referente al candidato: "+idCandidato);
				return 0;
			}
			else if(verificacion==2){
				System.out.println("Se actualizo la opinion creada por: "+autor +" en: "+fechaCreacion+", referente al candidato: "+idCandidato);
				return 1;
			}
			else if(verificacion==0){
				PreparedStatement stmnt=con.prepareStatement("INSERT INTO opinion (opinion_id, cdto_id, opinion_fecha, opinion_sentimiento, opinion_pais_ubicacion, opinion_region_ubicacion, opinion_ciudad_ubicacion, opinion_retweets, opinion_likes, opinion_autor, opinion_menciona_candidato, opinion_resp_candidato) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmnt.setInt(1,idCandidato);
				stmnt.setTimestamp(2, fechaCreacion);
				stmnt.setInt(3,sentimiento);
				stmnt.setString(4, pais);
				stmnt.setString(5, region);
				stmnt.setString(6, ciudad);
				stmnt.setInt(7, cantidadRetweets);
				stmnt.setInt(8, cantidadLikes);
				stmnt.setString(9, autor);
				stmnt.setBoolean(10, mencionaCandidato);
				stmnt.setBoolean(11, respuestaCandidato);
				int res = stmnt.executeUpdate();
				return res;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	
	
	
	//Metodo que inserta una metrica en la base de datos mysql
	public int insertarMetrica(String nombreMetrica, double valorMetrica, Timestamp fechaCreacionMetrica, int idCandidato){
		PreparedStatement stmnt;
		int res=0;
		try {
			PreparedStatement ps1 = con.prepareStatement("SELECT m.met_id FROM metrica m WHERE m.met_nombre=? AND m.met_valor=? AND m.met_fecha=?");
			ps1.setString(1,nombreMetrica);
			ps1.setDouble(2, valorMetrica);
			ps1.setTimestamp(3, fechaCreacionMetrica);
			ResultSet rs = ps1.executeQuery();
			if(rs.next()){
				//Si se encuentra la metrica no se inserta y se retorna error
				System.out.println("Ya existe esta metrica en la bd");
				return 0;
			}
			else{
				stmnt = con.prepareStatement("INSERT INTO metrica (met_nombre, met_valor, met_fecha) VALUES (?, ?, ?)");
				stmnt.setString(1,nombreMetrica);
				stmnt.setDouble(2, valorMetrica);
				stmnt.setTimestamp(3,fechaCreacionMetrica);
				res = stmnt.executeUpdate();
				if(res==1){
					ResultSet rs1 = consultar("SELECT MAX(met_id) AS id FROM metrica");
					if(rs1.next()){
						PreparedStatement ps = con.prepareStatement("INSERT INTO cdto_met (met_id, cdto_id) VALUES (?,?)");
						ps.setInt(1, rs1.getInt("id"));
						ps.setInt(2, idCandidato);
						int res1 = ps.executeUpdate();
						return res1;
					}
					else{
						System.out.println("Se creo la metrica, pero hay error al crear la relacion de la metrica y el candidato");
						return 2;
					}
				}
				else{
					return 0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//TODOS ESTOS METODOS SON PARA CALCULAR LA APROBACION NACIONAL!!!!
	
	//Metodo que retorna la suma de todos los likes de todas las opiniones positivas en la base de datos mysql
	public int obtenerCantidadLikesOpinionesPositivas(){
		int cantidad=0;
		ResultSet totalOpPosLikes = consultar("SELECT SUM(opinion_likes) AS TotalLikesTweetsPos FROM opinion WHERE opinion_sentimiento=1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none')");
		try {
			if(totalOpPosLikes.next()){
				cantidad = totalOpPosLikes.getInt("TotalLikesTweetsPos");
				//System.out.println("en el metodo: cantidad:  "+cantidad);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cantidad;
	}
	
	//Metodo que retorna la suma de todos los likes de todas las opiniones positivas referentes a un candidato en la base de datos mysql
		public int obtenerCantidadLikesOpinionesPositivasCand(int idCandidato){
			int cantidad=0;
			ResultSet totalOpPosLikesCand = consultar("SELECT SUM(opinion_likes) AS TotalLikesTweetsPosCand FROM opinion WHERE opinion_sentimiento =1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none') AND cdto_id="+idCandidato);
			try {
				if(totalOpPosLikesCand.next()){
					cantidad = totalOpPosLikesCand.getInt("TotalLikesTweetsPosCand");
					//System.out.println("en el metodo: cantidad:  "+cantidad);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cantidad;
		}
	
	//Metodo que retorna el total de opiniones positivas en la base de datos mysql
	public int obtenerCantidadOpinionesPositivas(){
		int cantidad=0;
		ResultSet totalOpPos= consultar("SELECT COUNT(*) AS TotalTweetsPos FROM opinion WHERE opinion_sentimiento =1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none')");
		try {
			if(totalOpPos.next()){
				cantidad= totalOpPos.getInt("TotalTweetsPos");
				//System.out.println("en el metodo: cantidad:  "+cantidad);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cantidad;
	}
	
	//Metodo que retorna el total de opiniones positivas referentes a un candidato en la base de datos mysql
		public int obtenerCantidadOpinionesPositivasCand(int idCandidato){
			int cantidad=0;
			ResultSet totalOpPosCand= consultar("SELECT COUNT(*) AS TotalTweetsPosCand FROM opinion WHERE opinion_sentimiento =1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none') AND cdto_id="+idCandidato);
			try {
				if(totalOpPosCand.next()){
					cantidad= totalOpPosCand.getInt("TotalTweetsPosCand");
					//System.out.println("en el metodo: cantidad:  "+cantidad);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cantidad;
		}
		
		//Metodo que retorna la suma de todos los rts de todas las opiniones positivas en la base de datos mysql
		public int obtenerCantidadRtsOpinionesPositivas(){
			int cantidad=0;
			ResultSet totalOpPosRts = consultar("SELECT SUM(opinion_retweets) AS TotalRtsTweetsPos FROM opinion WHERE opinion_sentimiento =1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none')");
			try {
				if(totalOpPosRts.next()){
					cantidad = totalOpPosRts.getInt("TotalRtsTweetsPos");
					//System.out.println("en el metodo: cantidad:  "+cantidad);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cantidad;
		}
		
		//Metodo que retorna la suma de todos los likes de todas las opiniones positivas referentes a un candidato en la base de datos mysql
			public int obtenerCantidadRtsOpinionesPositivasCand(int idCandidato){
				int cantidad=0;
				ResultSet totalOpPosRtsCand = consultar("SELECT SUM(opinion_retweets) AS TotalRtsTweetsPosCand FROM opinion WHERE opinion_sentimiento =1 AND (opinion_pais_ubicacion='Chile' OR opinion_pais_ubicacion='none') AND cdto_id="+idCandidato);
				try {
					if(totalOpPosRtsCand.next()){
						cantidad = totalOpPosRtsCand.getInt("TotalRtsTweetsPosCand");
						//System.out.println("en el metodo: cantidad:  "+cantidad);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return cantidad;
			}

	//TODOS ESTOS METODOS SON PARA CALCULAR LA APROBACION POR REGION
}
