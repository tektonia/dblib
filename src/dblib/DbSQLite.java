package dblib;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  Cria uma ligação a uma base de dados remota
 * 
 * @author 2014 - Vitor Vaz da Silva
 *
 */
public class DbSQLite extends DbSQL{
	
	String erroLigacao="DbSQLite: org.sqlite.JDBC missing.\n";
	
	/**
	 * <h3>DbSQLite</h3>
	 * <pre>public BaseDadosSQLite(String name)</pre>
	 * <p>Creates the necessary structure for a SQLite database connection</p>
	 *   
	 * @param name - Database name (if missing <code>"DbSQLite"</code> is used as default)
	 */	
	public DbSQLite(String name) {
	  nomeBd=name;
	  if(nomeBd==null || nomeBd.equals("")) nomeBd="DbSQLite"; // default name
	  try {
	    Class.forName("org.sqlite.JDBC");  // Inicializar a classe JDBC
	    ligacao = DriverManager.getConnection("jdbc:sqlite:"+nomeBd);
	    connect();
	    type="SQLite";
	    erroLigacao="DbSQLite: Database <"+nomeBd+"> opened with success\n";
	  } catch (ClassNotFoundException e) {
		  erroLigacao+="DbSQLite: ERROR: "+e.getClass().getName() + ": " + e.getMessage()+"\n";
		  erroLigacao+="ERRO: Probably the library sqlite-jdbc-xxx.jar is not set in the Java-Build-Path Libraries\n";
	  } catch (SQLException e) {
		  erroLigacao="DbSQLite: Error while opening <"+nomeBd+"> database: "+e.getMessage()+"\n";
	  } catch ( Exception e ) {
		 erroLigacao="DbSQLite: ERROR: "+e.getClass().getName() + ": " + e.getMessage()+"\n";
	  }
	  //BDebug.visible(true);
	  BDebug.write( erroLigacao);
	}
	
	/**
	 * <h3>lock</h3>
	 * <pre>public lock()</pre>
	 * <p>The database connection avoids other program to use the database</p>
	 *   
	 */		
	@Override
	public void lock(){
		String error="No error";
		try {
			ligacao=DriverManager.getConnection("jdbc:sqlite:"+nomeBd);
		} catch (SQLException e) {
			error="DbSQLite.lock: Unable to lock database:"+e.getMessage();
		}
		BDebug.write(error);
	}
	
	/**
	 * <h3>unlock</h3>
	 * <pre>public unlock()</pre>
	 * <p>By closing the connection the database can be used by another program</p>
	 *   
	 */	
	@Override
	public void unLock(){
		String error="No error";
		try {
			ligacao.close();
		} catch (SQLException e) {
			error="DbSQLite.unlock: Error while unlocking database:"+e.getMessage();
		}
		BDebug.write(error);
	}	
}
