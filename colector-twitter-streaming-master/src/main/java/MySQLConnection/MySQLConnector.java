package MySQLConnection;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Connection;

public class MySQLConnector {
	public Connection con;
	public String user;
	public String pass;
	private static final String url="jdbc:mysql://localhost:3306/votweet";
	
	
	public MySQLConnector(String userMySqlServer, String passMySqlServer){
		try{
			this.user=userMySqlServer;
			this.pass=passMySqlServer;
			this.con=DriverManager.getConnection(url,this.user, this.pass);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cargarKeywords(){
		ResultSet keywords = consultar("SELECT kwd_texto FROM keyword");
			FileWriter fichero = null;
	        PrintWriter pw = null;
	        try{
	        	String directorio="src/main/resources/words.dat";
	        	fichero = new FileWriter(directorio);
        		pw = new PrintWriter(fichero);
	        	while(keywords.next()){
	        		String keyword= keywords.getString("kwd_texto");
	        		pw.println(keyword);
	        	}
	         }catch (Exception e) {
	            e.printStackTrace();
	         }finally {
	           try {
	           // Nuevamente aprovechamos el finally para 
	           // asegurarnos que se cierra el fichero.
	           if (null != fichero)
	              fichero.close();
	           } catch (Exception e2) {
	              e2.printStackTrace();
	           }
	        }
		}
	
	public ArrayList<String> obtenerCuentasOficiales(){
		ArrayList<String> cuentasOficiales = new ArrayList<String>();
		ResultSet cuentas = consultar("SELECT cdto_cuenta_twitter FROM candidato");
		try {
			while(cuentas.next()){
				cuentasOficiales.add(cuentas.getString("cdto_cuenta_twitter"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cuentasOficiales;
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
	
}
