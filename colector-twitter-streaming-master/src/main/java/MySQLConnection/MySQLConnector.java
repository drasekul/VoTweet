package MySQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnector {
	public Connection con=null;
	public String user;
	public String pass;
	private static final String url="jdbc:mysql://localhost:3306/votweet";
	
	public MySQLConnector(String userMySqlServer, String passMySqlServer){
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
