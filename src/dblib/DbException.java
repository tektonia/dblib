package dblib;

public class DbException extends Exception{
	
	enum Causa {SQL_EXCEPTION, LINK_EXCEPTION, };
	
	Causa causa;
	
	public DbException(String msg, Causa c) {
        super(msg); causa=c;
		BDebug.write(msg);
    }

    public DbException(String msg, Throwable cause, Causa c) {
        super(msg, cause); causa=c;
		BDebug.write(msg);
    }

    public boolean isSQLException(){ return causa==Causa.SQL_EXCEPTION;}
    
    public boolean isLinkException(){ return causa==Causa.LINK_EXCEPTION;}
}
