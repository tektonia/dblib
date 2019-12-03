package dblib;

import java.sql.Connection;

//DCL - Data Description Language
//
// Commands TCL = {"COMMIT", "SAVEPOINT", "ROLLBACK", "SET"};
//
class TypeCmdTCL implements TypeCommandSQL{
	
	protected TypeCmdTCL(){}	
	
	@Override
	public DbResult execute(Connection conn, String cmd) {
		BDebug.write("\nTCL: "+cmd);
		String msg="Method TipoCmdTCL.execute() is not coded!";
		BDebug.write(msg);
		return new DbResult(null, -1, msg);
	}
}
