package dblib;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to give support to the results of a SQL command over a database
 * Associated to each response, that can be nonexistent, there is a response that
 * says more than the success or unsuccessful result of an operation.
 * 
 * @author Victorix
 *
 */
public class DbResult {
  private ResultSet resSet;
  private String mensagem;
	
  DbResult(ResultSet res, String msg){
	resSet=res;
	mensagem= msg==null ? "" : msg;
  }
	
  /**
   * <h4>resultSet</h4>
   * <pre>public ResultSet resultSet()</pre>
   * 
   * <p>Returns the response structure (raw) of a SQL command.</p>
   * 
   * @return - object of type ResultSet
   */
  public ResultSet resultSet() {return resSet;}
	
  /**
   * <h4>message</h4>
   * <pre>public String message()</pre>
   * <p>Returns a message concerning the success or not of an execution of a SQL command</p>
   * 
   * @return - A string with the message.
   */
  public String message() {return mensagem;}
	
  /**
   * <h4>attributes</h4>
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
   * <h4>rows</h4>
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
}
