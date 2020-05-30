package eu.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eu.polimi.tiw.common.Config;

public class ProjectDao extends GenericDao {

	private int idProgetto;
	private final String SEARCH_QUERY = "query.ricercaprogetto";

	public ProjectDao(Connection conn) {
		super(conn);
	}

	public ProjectDao(int idProgetto, Connection conn) {
		super(conn);
		this.idProgetto = idProgetto;
	}

	public ResultSet searchSingleProject() {
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

	public ResultSet getAllProjects() {
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
		whereConditionToReturn.append(" where id_progetto='");
		whereConditionToReturn.append(this.idProgetto);
		whereConditionToReturn.append("';");
		return whereConditionToReturn;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

}
