package dblib;

import java.sql.Connection;

//DCL - Data Description Language
//
// Commands type DCL = {"GRANT", "REVOKE"};
//
public class TypeCmdDCL implements TypeComandoSQL{
	
	protected TypeCmdDCL(){}
	
	@Override
	public DbResult execute(Connection conn, String cmd) {
		BDebug.write("DCL: "+cmd);
		String msg="Method TipoCmdDCL.execute() is not coded!";
		BDebug.write(msg);
		DbSQL.setLastError(msg);
		return new DbResult(null, msg);
	}


}
