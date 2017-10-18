package dblib;

import java.sql.Connection;

public interface TypeComandoSQL {
	/**
	 * execute
	 *   Executes a SQL command <code>cmd</code> to a database connection <code>conn</code>
	 *   
	 * @param conn
	 * @param cmd
	 * @return
	 */
	DbResult execute(Connection conn, String cmd);
}
