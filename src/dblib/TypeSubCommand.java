package dblib;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import dblib.TypeSubCommand.SubCmd;

public abstract class TypeSubCommand implements TypeCommandSQL{
	String texto;
	static HashMap <String, SubCmd>subComandos = new HashMap<String, SubCmd>();

	interface SubCmd{
		public DbResult execute(Connection conn, String cmd) throws SQLException;
	}
	
	TypeSubCommand(String str, String [] cmds, SubCmd [] subCmds){
		texto= str;
		int i=0;
		for(String c:cmds) subComandos.put(c, subCmds[i++]);		
	}
	
	public DbResult execute(Connection conn, String cmd){
	  DbResult res=new DbResult(null, -1, "Syntax error or inexistent command.");
	  if(DbSQL.getDebugLevel()>1) BDebug.write("\n"+texto+" "+cmd);
	  // verify if there is a ; terminating the string
	  if(cmd.charAt(cmd.length()-1)==';') cmd=cmd.substring(0, cmd.length()-1);  //remove the ;
	  else{
		String msg = "Syntax error: <"+cmd+">";
		BDebug.write(msg); 
		return new DbResult(null, -1, msg);
	  }
	  // execute the command that is on the second word of the string	
	  String [] str = cmd.split(" ");
	  SubCmd subCmd=subComandos.get(str[1].toUpperCase());
	  if(subCmd!=null){
		try {	  
			if(DbSQL.getDebugLevel()>1) BDebug.write("\nSUB: "+cmd);
		  res=subCmd.execute(conn, cmd);
		} catch (SQLException e) {
		  res = new DbResult(null, -1, "Execution error!");
		}
	  }
	  if(DbSQL.getDebugLevel()>1) BDebug.write(" msg<"+res.message()+"> cmd: "+cmd);
	  return res;
	}
}
