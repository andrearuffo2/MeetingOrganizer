package eu.polimi.tiw.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class will manage db connection.
 */
public class DbConnection {

	private static DbConnection instance;

	private DbConnection() {
	}

	// Lazy initialization singleton pattern
	public static DbConnection getInstance() {
		if (instance == null) {
			instance = new DbConnection();
		}
		return instance;
	}

	public Connection getConnection() {

		Connection connection = null;
		try {

			connection = DriverManager.getConnection(Config.getInstance().getProperty("db.url"),
					Config.getInstance().getProperty("db.user"), Config.getInstance().getProperty("db.password"));

		} catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return connection;
	}
}