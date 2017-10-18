package dblib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// DML - Data Manipulation Language
//
// Commands type DML = {"SELECT", "INSERT", "UPDATE", "DELETE", "MERGE",
//						"UPSERT", "CALL", "EXPLAIN", "LOCK"};
//
// Comandos "INSERT", "UPDATE", "DELETE", "LOAD" are called as DDL due to their execution method 
//
public class TypeCmdDML implements TypeComandoSQL{
	
	protected TypeCmdDML(){}
	
	public DbResult execute(Connection conn, String cmd) {
		ResultSet res=null;	
		String msg=null;
		BDebug.write("\nDML: "+cmd);
		try {
			conn.setAutoCommit(false);
			Statement declaracao = conn.createStatement();
			res = declaracao.executeQuery(cmd);
			conn.commit();
			DbSQL.clearLastError();
		} catch (SQLException e) {
			msg=e.getMessage();
			msg="\nSQLException: "+msg+"\n";
			BDebug.write(msg); 
			DbSQL.setLastError(msg);
		}
		return(new DbResult(res, msg));
	}

}
