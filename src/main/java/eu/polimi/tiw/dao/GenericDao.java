package eu.polimi.tiw.dao;

import java.sql.Connection;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class define the generic behaviour for a DAO.
 */
public abstract class GenericDao {

	private Connection dbConnection;;

	public GenericDao() {
		super();
	}

	public GenericDao(Connection conn) {
		super();
		this.dbConnection = conn;
	}

	/**
	 * @return the string with the where condition to attach to select query.
	 */
	public abstract StringBuilder whereCondition();

	public Connection getConn() {
		return dbConnection;
	}

}
