package eu.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.Config;
import eu.polimi.tiw.repository.ProjectUserRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class has method to query the utenti table on the db.
 */
public class UserProjectDao extends GenericDao {

	// Index on rendicontazione table
	private int idUtente;
	private int idProgetto;

	private final String SEARCH_QUERY = "query.utenteprogetto";
	private final String SEARCH_QUERY_PROGETTI_UTENTE = "query.listaprogettiutente";
	private final String INSERT_QUERY = "query.inserisciutenteprogetto";

	/**
	 * Default constructor
	 */
	public UserProjectDao(Connection conn) {
		super(conn);
	}

	public UserProjectDao(int idUtente, int idProgetto, Connection conn) {
		super(conn);
		this.idUtente = idUtente;
		this.idProgetto = idProgetto;
	}

	public ResultSet searchUtenteProgetto() throws AppCrash, SQLException {

		Statement statement = getConn().createStatement();
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
		queryToExecute.append(whereCondition());
		queryToExecute.append("';");
		ResultSet rs = statement.executeQuery(queryToExecute.toString());
		rs.close();
		statement.close();
		return rs;

	}

	public ResultSet searchProgettiUtente() throws AppCrash, SQLException {

		Statement statement = getConn().createStatement();
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY_PROGETTI_UTENTE));
		queryToExecute.append(whereConditionProgetti());
		queryToExecute.append("';");
		ResultSet rs = statement.executeQuery(queryToExecute.toString());
		return rs;

	}

	/**
	 * @param report
	 *            The logic behind the program does permit only insert day by day.
	 *            So is not necessary to create a bulk insert of days
	 * @throws SQLException
	 *
	 */
	public void insertUtenteProgetto(ProjectUserRepository projectUser) throws SQLException {

		StringBuilder updateQuery = new StringBuilder();
		Statement statement = getConn().createStatement();
		updateQuery.append(Config.getInstance().getProperty(INSERT_QUERY));
		updateQuery.append(projectUser.getIdProgetto());
		updateQuery.append(",");
		updateQuery.append(projectUser.getIdUtente());
		updateQuery.append(");");

		statement.executeUpdate(updateQuery.toString());

		statement.close();

	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	@Override
	public StringBuilder whereCondition() {

		StringBuilder whereConditionToReturn = new StringBuilder();
		whereConditionToReturn.append(" where idUtente='");
		whereConditionToReturn.append(this.idUtente);
		whereConditionToReturn.append(" and ");
		whereConditionToReturn.append(" idProgetto='");
		whereConditionToReturn.append(this.idProgetto);
		return whereConditionToReturn;
	}

	public StringBuilder whereConditionProgetti() {

		StringBuilder whereConditionToReturn = new StringBuilder();
		whereConditionToReturn.append(" where t1.id_utente='");
		whereConditionToReturn.append(this.idUtente);
		return whereConditionToReturn;
	}

}
