package dblib;

import java.util.ArrayList;

public class DbUtils {
	
    interface AnotherNewLine{
		void NewLine(DbRow r);
	};

	/**
	 * <h4>getNumber</h4>
	 * <pre>public static int getNumber(DbResult res)</pre>
	 * <br>Gets the first number from the result<br>
	 * @param res - DbResult from an SQL execution
	 * @return - a number
	 */
	public static int getNumber(DbResult res){
		int size=0;
		try{
		  DbTable tab=res.rows();
		  size=tab.size();
		  String str = tab.get(0).get(0).toString(); // 1st line, 1st attribute
		  return Integer.parseInt(str);
		}
		catch (Exception e){BDebug.write("getNumber - rowCount: "+size+" Error: "+res.message()+" Exception: "+e.getMessage());}
		return -1;
	}
	
	/**
	 * <h4>getString</h4>
	 * <pre>public static String getString(DbResult res)</pre>
	 * <br>Gets the first string from the result<br>
	 * @param res - DbResult from an SQL execution
	 * @return - a string
	 */	
	public static String getString(DbResult res){
		String str="";
		int size=0;
		try{
		  DbTable tab=res.rows();
		  size=tab.size();
		  str= tab.get(0).get(0).toString(); // 1st line, 1st attribute
		}
		catch (Exception e){
			BDebug.write("getStringArray - rowCount: "+size+" Error: "+res.message()+" Exception: "+e.getMessage());
			str=res.message()+" "+e.getMessage();
		}
		return str;
	}
  	
	/**
	 * <h4>getStringArray</h4>
	 * <pre>public static String [] getStringArray(DbResult res)</pre>
	 * <br>Gets an array of strings from the result<br>
	 * @param res - DbResult from an SQL execution
	 * @return - a string array in which each String is a row of the SQL result
	 */		
	public static String [] getStringArray(DbResult res){
		int size=0;
		try{
		  DbTable tab=res.rows();
		  size=tab.size();
		  int i=0;
		  String s[] = new String[tab.size()];
		  for(DbRow l  : tab){
			  s[i++]= l.get(0).toString();
		  }
		  return s;
		}
		catch (Exception e){BDebug.write("getStringArray - rowCount: "+size+" Error: "+res.message()+" Exception: "+e.getMessage());}
		return null;
	}	
	
	/**
	 * <h4>getBoolean</h4>
	 * <pre>public static boolean getBoolean(DbResult res)</pre>
	 * <br>Gets the first boolean from the result<br>
	 * @param res - DbResult from an SQL execution
	 * @return - a boolean
	 */
	public static boolean getBoolean(DbResult res){
		int size=0;
	    try{
		   DbTable tab=res.rows();
		   size=tab.size();
		   String str = tab.get(0).get(0).toString(); // 1st line, 1st attribute
			  return Boolean.parseBoolean(str);
	    }
		catch (Exception e){BDebug.write("getBoolean - rowCount: "+size+" Error: "+res.message()+" Exception: "+e.getMessage());}
		return false;
	}
	
	public  static <T> ArrayList <T> getObjects(DbResult res){
		DbTable tab=null;
		 BDebug.write("getObjects: entrou: "+res.message());
		ArrayList <T>  dados =null;
		try{
		  tab=res.rows();
		  BDebug.write("getObjects: "+tab.size());
		  dados = new ArrayList <T> ();
		  for(DbRow lin : tab){
			  T p= (T) new Object();
			  p=(T) p.getClass().getConstructor(DbRow.class).newInstance(lin);
			  dados.add(p);
		  }
		}
		catch (Exception e){
			BDebug.write("getObjects "+(tab==null?"null":tab.size())+" Exception:"+e.getMessage());
		}
		return dados;
	}	

	public  static ArrayList <DbRow> getRows(DbResult res){
		ArrayList <DbRow>lista=new ArrayList<DbRow>();
		DbTable tab=null;
		try{
		  tab=res.rows();
		  BDebug.write("getObjects: "+tab.size());
		  for(DbRow lin : tab){
			  lista.add(lin);
		  }
		}
		catch (Exception e){
			BDebug.write("getObjects "+(tab==null?"null":tab.size())+" Exception:"+e.getMessage());
		}
		return lista;
	}	
}
