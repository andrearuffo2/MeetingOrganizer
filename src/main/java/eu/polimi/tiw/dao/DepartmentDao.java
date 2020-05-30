package eu.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.Config;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class has method to query the utenti table on the db.
 */
public class DepartmentDao extends GenericDao {

	private String dipartimento;
	private final String SEARCH_QUERY = "query.ricercadipartimento";

	/**
	 * Default constructor
	 */
	public DepartmentDao(Connection conn) {
		super(conn);
	}

	public DepartmentDao(String dipartimento, Connection conn) {
		super(conn);
		this.dipartimento = dipartimento;
	}

	public ResultSet searchDepartment() throws AppCrash {
		Statement statement;
		ResultSet rs = null;
		try {
			statement = getConn().createStatement();
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
			queryToExecute.append(whereCondition());
			rs = statement.executeQuery(queryToExecute.toString());
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	public ResultSet getAllDepartment() {
		Statement statement;
		ResultSet rs = null;
		try {
			statement = getConn().createStatement();
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
			rs = statement.executeQuery(queryToExecute.toString());
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	@Override
	public StringBuilder whereCondition() {

		StringBuilder whereConditionToReturn = new StringBuilder();
		whereConditionToReturn.append(" where nome='");
		whereConditionToReturn.append(this.dipartimento);
		whereConditionToReturn.append("';");
		return whereConditionToReturn;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}
}
