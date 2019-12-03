package dblib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//DDL - Data Description Language
//
// Commands type DDL = {"CREATE", "ALTER", "DROP", "TRUNCATE",
//						"COMMENT", "RENAME"};
//
// Although not belonging to the DDL set, commands "LOAD", "INSERT", "UPDATE", "DELETE"
// are also called here due to the use of the same method described.
//
public class TypeCmdDDL implements TypeCommandSQL{
	int numeroDeSequencia=0;
	
	protected TypeCmdDDL(){}
	
		@Override
	public DbResult execute(Connection conn, String cmd) throws DbException {
		if(DbSQL.getDebugLevel()>=1) BDebug.write("\nDDL: "+cmd);
		String msg=null;
		int errCode=-1;
		try {
			conn.setAutoCommit(false);
			Statement declaracao = conn.createStatement();
			numeroDeSequencia = declaracao.executeUpdate(cmd);
			declaracao.close();
			conn.commit();
			errCode=0;
		} catch (SQLException e) {
			numeroDeSequencia=-1;
			msg=e.getMessage();
			errCode=e.getErrorCode();
			if(errCode==0) errCode=-1;
			msg="\nSQLException: "+errCode+" "+msg+"\n";
			throw new DbException(msg, DbException.Causa.SQL_EXCEPTION); 
		}
		return(new DbResult(numeroDeSequencia, errCode, msg));
	}

}
