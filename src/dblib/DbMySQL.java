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
public class DbMySQL extends DbSQL{
	String link;
	String user;
	String pwd;
	
	/**
	 * <h3>DbJDBC</h3>
	 * <pre>public DbJDBC(String url, String user, String pwd, String nomeBd)</pre>
	 * <p>Creates the structure for the connection to a remote database</p>
	 * 
	 * @param url - Remote database URL
	 * @param usr - Database user
	 * @param pw - User password
	 * @param nomeBd - Database name
	 */
	public DbMySQL(String url, String usr, String pw, String nomeBd) {
		String driver = "com.mysql.jdbc.Driver";
		String error="";	
		user=usr;
		pwd=pw;
		if(url==null || user==null || pwd==null || nomeBd== null) return;
		link = "jdbc:mysql://"+url+"/"+nomeBd;
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
	}

	/**
	 * <h3>lock</h3>
	 * <pre>public lock()</pre>
	 * <p>The database connection avoids other program to use the database</p>
	 *   
	 * /		
	@Override
	public void lock(){
		String error="No error";
		try {
			ligacao=DriverManager.getConnection(link, user, pwd);
		} catch (SQLException e) {
			error="DbMYSQL.lock: Unable to lock database:"+e.getMessage();
		}
		BDebug.write(error);
	}
	
	/**
	 * <h3>unlock</h3>
	 * <pre>public unlock()</pre>
	 * <p>By closing the connection the database can be used by another program</p>
	 *   
	 * /	
	@Override
	public void unLock(){
		String error="No error";
		try {
			ligacao.close();
		} catch (SQLException e) {
			error="DbMYSQL.unlock: Error while unlocking database:"+e.getMessage();
		}
		BDebug.write(error);
	}	
	*/
}
