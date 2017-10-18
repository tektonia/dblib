package dblib;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *  Cria uma ligação a uma base de dados remota
 * 
 * @author 2014 - Vitor Vaz da Silva
 *
 */
public class DbJDBC extends DbSQL{

	/**
	 * <h4>DbJDBC</h4>
	 * <pre>public DbJDBC(String url, String user, String pwd, String nomeBd)</pre>
	 * <p>Creates the structure for the connection to a remote database</p>
	 * 
	 * @param url - Remote database URL
	 * @param user - Database user
	 * @param pwd - User password
	 * @param nomeBd - Database name
	 */
	public DbJDBC(String url, String user, String pwd, String nomeBd) {
		String driver = "com.mysql.jdbc.Driver";
		String error="";	
		if(url==null || user==null || pwd==null || nomeBd== null) return;
		String link = "jdbc:mysql://"+url+"/"+nomeBd;
        try {
        	Class.forName(driver);
            ligacao = DriverManager.getConnection(link, user, pwd);
            connect();
            type="MYSQL";
            error="DbJDBC: opened <"+url+"> <"+nomeBd+">";
        } catch (ClassNotFoundException ex) {
            error="DbJDBC: MySql Connector/J <"+driver+"> is not installed.";
        } catch (SQLException ex) {
            error="DbJDBC: Network connection error,\n\tor credential error url<"+url+"> database<"+nomeBd+">, or  Connector/J driver not installed."+
            		": " + ex.getMessage()+"\n";
        } catch ( Exception e ) {
   	        error= "DbJDBC: ERRO: "+e.getClass().getName() + ": " + e.getMessage()+"\n";
  	  }
      BDebug.write(error);
      setLastError(error);
	}
}
