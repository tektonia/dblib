package dblib;

import java.util.ArrayList;

public class DbTable extends ArrayList <DbRow>{
	private static final long serialVersionUID = 1L;
	protected ArrayList <String> nomeAtributo = new ArrayList <String>();
	protected ArrayList <String> nomeTabela = new ArrayList <String>();
	
	public DbTable(){}
	
	protected void addNomeAtributo(String nome){ nomeAtributo.add(nome);}
	
	protected void addNomeTabela(String nome){ nomeTabela.add(nome);}
		
	public String [] columnNames(){ return (String []) nomeAtributo.toArray(); }
	
	public String columnName(int n){ return nomeAtributo.get(n); }
	
	public String [] tableNames(){ return (String []) nomeTabela.toArray(); }
	
	public String tableName(int n){ return nomeTabela.get(n); }
	
	public int numColumns(){ return nomeAtributo.size(); }
	
	public ArrayList<String> attributes(){ return nomeAtributo;}
	
	public String toString(){
		String str="";
		int n=nomeAtributo.size();
		for( int i=0; i<n; i++ ){
			try{str+=nomeTabela.get(n);} catch(Exception e){}
			try{str+="."+nomeAtributo.get(n)+"\t";} catch(Exception e){}
		}
		return str.trim();
	}
}
