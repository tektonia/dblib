package dblib;
/*
 * Programa de exemplo para a disciplina de Base de Dados - LEETC, MEETC
 * 
 * 2014 - Vitor Vaz da Silva
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * DbSQL
 * Creates the structure needed for a local or remote database
 * 
 * @author 2014 - Vitor Vaz da Silva
 *
 */
public class DbSQL {
	static HashMap <String, TypeComandoSQL>tipoComandos = new HashMap<String, TypeComandoSQL>();
	static Connection ligacao=null; // this field is updated by the connection: JDBC, ODBC, SQLite, ...
	static String nomeBd;
	static String lastError="No error";
	static String type="UNKOWN";
	
	/**
	 * <h4>connect</h4>
	 * <pre>public boolean connect()</pre>
	 * <p>Established a connection to the database</p>
	 * 
	 * @return false - Not possible to establish a connection to the database<br>
	 *         true  - Database connection established.
	 */
	public boolean connect(){
		boolean res=false;
		String error="DbSQL.connect: No connection to the database. Unreachable!";
		if(ligacao!=null){
		  try {
				init();
				Statement cmd=ligacao.createStatement(); // para verificar
				cmd.close();
				error="\nDbSQL.connect: Connected to the database.";
				res=true;
			} catch (SQLException e) {
				error="DbSQL.connect: Error connecting to the database. SQLException: "+e.getMessage();
			}	
		}
		BDebug.write(error);
		return res;
	}

	/**
	 * <h4>disconnect</h4><pre>public void disconnect()</pre>
	 * <p>Disconnects the connection to the database, even if it is not active.</p>
	 * 
	 */
	public void disconnect(){
	  try {
	    if(ligacao!=null) ligacao.close();
	  } catch (SQLException e) {
	    BDebug.write("DbSQL.disconnect: ERROR closing the connection: "+e.getMessage());
	  }
	  ligacao=null;
	  BDebug.write("\nDbSQL.disconnect: Database disconnected.");
	}
	
	/*
	 * <h4>init</h></h4><pre>protected static void init(Connection conn)</pre><p>
	 *  Inserts all commands in a hash table where each one is associated to the most adequate command to be executed.
	 * </p>
	 */
	protected static void init(){
		//Commands divided by type. Write in UPPERCASE!
		String [] DDL = {"LOAD", "CREATE", "INSERT", "UPDATE", "DELETE", "ALTER", "DROP", "TRUNCATE", "COMMENT", "RENAME"};
		String [] DML = {"SELECT", "MERGE", "UPSERT", "CALL", "EXPLAIN", "LOCK"};
		String [] DCL = {"GRANT", "REVOKE"};
		String [] TCL = {"COMMIT", "SAVEPOINT", "ROLLBACK", "SET"};		
		String [] MetaData = {"SHOW"};	
		//NOTE: Insert, Update and  Delete are in DDL because of the methods that have to be 
		//  invoked, effectively they are DML type of commands.
		//
		TypeCmdDDL ddl = new TypeCmdDDL();
		for(String str:DDL) tipoComandos.put(str, ddl);
		TypeCmdDML dml = new TypeCmdDML();
		for(String str:DML) tipoComandos.put(str, dml);
		TypeCmdDCL dcl = new TypeCmdDCL();
		for(String str:DCL) tipoComandos.put(str, dcl);
		TypeCmdTCL tcl = new TypeCmdTCL();
		for(String str:TCL) tipoComandos.put(str, tcl);
		TypeCmdMDT mdt = new TypeCmdMDT();
		for(String str:MetaData) tipoComandos.put(str, mdt);
	}

	/**
	 * <h4>executeCommandSQL</h4>
	 * <pre>public static DbResult executeCommandSQL(String cmd)</pre>
	 * <p>Executes the SQL command</p>
	 *  
	 * @param cmd - SQL command to be executed
	 * @return null - in case of error or <br>DbResult object with the answer
	 */
	public DbResult executeCommandSQL(String comando){
		if(ligacao == null){
			BDebug.write("DbSQL.executeCommandSQL: Database is not open.");
			return null;
		}
		TypeComandoSQL cmd=tipoComandos.get(comando.split(" ")[0].toUpperCase());
		if(cmd!=null) return(cmd.execute(ligacao, comando));
		else return executeOtherTypeCmdSQL(comando);
	}

	/**
	 * <h4>executeOtherTypeCmdSQL</h4>
	 * <pre>private DbResult executeOtherTypeCmdSQL(String cmd)</pre>
	 * <p>Executes a SQL command that is not in the list filled in by init</p>
	 *  
	 * @param cmd - SQL command to be executed
	 * @return null - in case of error or <br>DbResult object with the answer
	 */
	private DbResult executeOtherTypeCmdSQL(String comando){
		 ResultSet res=null;
		 String error="No error...";
		 String msg="";
		try {
			ligacao.setAutoCommit(false);//true - cada comando é individual; false - está relacionado com outros - rollback ou commit para executar
			Statement declaracao =ligacao.createStatement();
			if(declaracao.execute(comando)){
				res = declaracao.getResultSet();
				ligacao.commit();
			}
		} catch (SQLException e) {
			msg=e.getMessage();
			error="\nSQLException DbSQL.executeOtherTypeCmdSQL: "+msg+"\n";
		}
		BDebug.write(error);
		setLastError(error);
		return new DbResult(res, msg);
	}
	
	/**
	 * <h4>isConnected</h4>
	 * <pre>public boolean isConnected()</pre>
	 * <p>Indicates if there is a connection to the database</p>
	 * 
	 * @return false - No connection to the database<br>
	 *         true  - Database connection is established.
	 */
	public boolean isConnected(){
		return ligacao!=null;
	}
	
	/**
	 * <h4>close</h4>
	 * 
	 * <p>public void close()</p>
	 * 
	 *   <p>Closes the connection</p>
	 */
	public void close(){
		disconnect();
	}
	
	public static String getType() { return type;}
	
	public static void clearLastError(){ lastError="no error"; }
	
	public static void setLastError(String str){ lastError=str; }
	
	public static String getLastError() { return lastError; }
	
	public void lock(){}
	
	public void unLock(){}
	
}
