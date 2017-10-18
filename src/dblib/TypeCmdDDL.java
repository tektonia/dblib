package dblib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//DDL - Data Description Language
//
// Commandss type DDL = {"CREATE", "ALTER", "DROP", "TRUNCATE",
//						"COMMENT", "RENAME"};
//
// Although not belonging to the DDL set, commands "LOAD", "INSERT", "UPDATE", "DELETE"
// are also called here due to the use of the same method described.
//
public class TypeCmdDDL implements TypeComandoSQL{
	int numeroDeSequencia=0;
	
	protected TypeCmdDDL(){}
	
		@Override
	public DbResult execute(Connection conn, String cmd) {
		BDebug.write("DDL: "+cmd);
		ResultSet res=null;	
		String msg=null;
		try {
			conn.setAutoCommit(false);
			Statement declaracao = conn.createStatement();
			numeroDeSequencia = declaracao.executeUpdate(cmd);
			declaracao.close();
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
