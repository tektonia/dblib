package dblib;

import java.sql.Connection;

public interface TypeCommandSQL{
	/**
	 * execute
	 *   Executes a SQL command <code>cmd</code> to a database connection <code>conn</code>
	 *   
	 * @param conn Connection
	 * @param cmd	SQL command
	 * @return DBResult
	 * @throws DbException SQL command execution error
	 */
	DbResult execute(Connection conn, String cmd) throws DbException;
}
