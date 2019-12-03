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
public class TypeCmdDML implements TypeCommandSQL{
	
	protected TypeCmdDML(){}
/*	
	public DbResult executeNoException(Connection conn, String cmd) {
		ResultSet res=null;	
		String msg=null;
		int errCode=-1;
		if(DbSQL.getDebugLevel()>=1) BDebug.write("\nDML: "+cmd);
		try {
			conn.setAutoCommit(false);
			Statement declaracao = conn.createStatement();
			res = declaracao.executeQuery(cmd);
			conn.commit();
			errCode=0;
		} catch (SQLException e) {
			msg=e.getMessage();
			errCode=e.getErrorCode();
			msg="\nSQLException: "+errCode+" "+msg+"\n";
			BDebug.write(msg); 
		}
		return(new DbResult(res, errCode, msg));
	}
*/
	@Override
	public DbResult execute(Connection conn, String cmd) throws DbException {
		ResultSet res=null;	
		String msg=null;
		int errCode=-1;
		if(DbSQL.getDebugLevel()>=1) BDebug.write("\nDML: "+cmd);
		try {
			conn.setAutoCommit(false);
			Statement declaracao = conn.createStatement();
			res = declaracao.executeQuery(cmd);
			conn.commit();
			errCode=0;
		} catch (SQLException e) {
			msg=e.getMessage();
			errCode=e.getErrorCode();
			msg="\nSQLException: "+errCode+" "+msg+"\n";
			throw new DbException(msg, DbException.Causa.SQL_EXCEPTION);
		}
		return(new DbResult(res, errCode, msg));
	}

}
