import java.sql.Connection;
import java.sql.DriverManager;


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
	
}
