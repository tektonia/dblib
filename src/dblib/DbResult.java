package dblib;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Class to give support to the results of a SQL command over a database
 * Associated to each response, that can be nonexistent, there is a response that
 * says more than the success or unsuccessful result of an operation.
 * 
 * @author Victorix
 *
 */
public class DbResult {
  private ResultSet resSet=null;
  private String mensagem;
  private int resNum=0;
  private int errCode=0;
	
  DbResult(ResultSet res, int err, String msg){
	resSet=res;
	errCode=err;
	mensagem= msg==null ? "" : msg;
  }
	
  DbResult(int num, int err, String msg){
	resNum=num;
	errCode=err;
	mensagem= msg==null ? "" : msg;
  }
  
  public int resNum() { return resNum;}
		  
  public int errCode() { return errCode;}
  
  /**
   * <h3>resultSet</h3>
   * <pre>public ResultSet resultSet()</pre>
   * 
   * <p>Returns the response structure (raw) of a SQL command.</p>
   * 
   * @return - object of type ResultSet
   */
  public ResultSet resultSet() {return resSet;}
	
  /**
   * <h3>message</h3>
   * <pre>public String message()</pre>
   * <p>Returns a message concerning the success or not of an execution of a SQL command</p>
   * 
   * @return - A string with the message.
   */
  public String message() {return mensagem;}
	
  /**
   * <h3>attributes</h3>
   * <pre>private Tabela attributes()</pre>
   * 
   * <p>Returns a DbTable object with the name of the attributes.</p>
   * 
   * @param res - Response structure of a SQL command
   * @return - a DbTable object with only the name of the attributes (columns)
   */
  private DbTable attributes(){ //ResultSet res){
	DbTable tab=new DbTable();
	try {
	  if(resSet!=null && ! resSet.isClosed()) {
	    ResultSetMetaData metadata = resSet.getMetaData();
	    int nCol=metadata.getColumnCount();
	    for(int i=1; i<=nCol; i++){
		  tab.addNomeAtributo(metadata.getColumnName(i));
		  tab.addNomeTabela(metadata.getTableName(i));
	    }
	  }
	}catch (SQLException e) {
		BDebug.write("DbResult.attributes: Error Result.numberOfColumns <"+e.getMessage()+">\n");
	}
	return tab;	  
  }
  
  /**
   * <h3>rows</h3>
   * <pre>public DbTable rows()</pre>
   * 
   * <p>Returns a DbTable of rows resultant from a SQL command.</p>
   * 
   * @return - a DbTable
   */
  public DbTable rows(){ 
	  if(resSet==null) return new DbTable();
	  int linha=0;
	  DbTable novaTabela = attributes(); 
	  int nCols=novaTabela.numColumns();  /* how many columns are there in the result */
	  try {
		while(resSet.next()){ /* while there are rows */
			DbRow novaLinha=new DbRow();
			for(int c=1; c<=nCols; c++){ //ATTENTION: First column has index 1 (i.e. does not start with 0)!
			  novaLinha.add(new DbAttribute(resSet.getString(c)));
			}
			novaTabela.add(novaLinha);
			linha++;
		}
	  } catch (SQLException e) {
		  BDebug.write("DbTable.rows: readSeveralRows: Error in row "+linha+" : "+e.getMessage());
	  }
	  return novaTabela;
	}

	/**
	 * <h3>getNumber</h3>
	 * <pre>public static int getNumber(DbResult res)</pre>
	 * <br>Gets the first number from the result<br>
	 * @param res - DbResult from an SQL execution
	 * @return - a number
	 */
	public int getNumber(DbResult res){
		int size=0;
		try{
		  DbTable tab=res.rows();
		  size=tab.size();
		  DbRow row=tab.get(0);
		  String str = row.get(0).toString(); // (line 0 - column names) 1st line, 1st attribute
		  return Integer.parseInt(str);
		}
		catch (Exception e){BDebug.write("getNumber - rowCount: "+size+" Error: "+res.message()+" Exception: "+e.getMessage());}
		return -1;
	}
	
	public int getNumber(){ return getNumber(this); }
	
	public void data(Vector <Vector>dados, Vector <String> atributos) {	
		if(resSet==null) return;
		DbTable tab = rows();
		ArrayList<String> att = tab.attributes();
		atributos.addAll(att);
		for(DbRow t :tab){
			Vector <String>aux = new Vector<String>();
			// utilizar o aux.addAll();
			for(DbAttribute s:t) aux.add(s.toString());
			dados.add(aux);
		}

	}	
}
