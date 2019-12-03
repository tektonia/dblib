package dblib;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;


// MDT - Meta Data
//
// Commands type MDT = {"SHOW"};
//
public class TypeCmdMDT extends TypeSubCommand{

	static String [] CMD = {"TABLES", "DATABASES"};

	static SubCmd CmdTables = new SubCmd(){
		@Override
		public DbResult execute(Connection conn, String cmd) throws SQLException {
          DatabaseMetaData md = conn.getMetaData();
          String catalog=null; 
          String schemaPattern=null;
          String tableNamePattern="%";
          String []type={"TABLE", "VIEW"}; //{"TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"};
          ResultSet rs=md.getTables(catalog, schemaPattern, tableNamePattern,type);
          return new DbResult(rs,-2,null);
		}
	};
			
	static SubCmd CmdDatabases = new SubCmd(){
		@Override
		public DbResult execute(Connection conn, String cmd)throws SQLException {
          DatabaseMetaData md = conn.getMetaData();
        //return md.getSchemas();  	// Different from MySQL
          return new DbResult(md.getCatalogs(),-2,null);  // MySQL 
		}
	};	
	
	static SubCmd [] comandos = {CmdTables, CmdDatabases};
	
	protected TypeCmdMDT(){
		super("MetaData", CMD, comandos);
	}

}