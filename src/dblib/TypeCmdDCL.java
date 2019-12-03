package dblib;

import java.sql.Connection;

//DCL - Data Description Language
//
// Commands type DCL = {"GRANT", "REVOKE"};
//
public class TypeCmdDCL implements TypeCommandSQL{
	
	protected TypeCmdDCL(){}
	
	@Override
	public DbResult execute(Connection conn, String cmd) {
		BDebug.write("\nDCL: "+cmd);
		String msg="Method TipoCmdDCL.execute() is not coded!";
		BDebug.write(msg);
		return new DbResult(null, -1,msg);
	}


}
