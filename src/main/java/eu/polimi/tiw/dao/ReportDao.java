package eu.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import eu.polimi.tiw.common.Config;
import eu.polimi.tiw.repository.ReportRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class has method to query the utenti table on the db.
 */
public class ReportDao extends GenericDao {

	// Index on rendicontazione table
	private int idUtente;
	private int idProgetto;
	private LocalDate dataRegistrata;

	private final String SEARCH_QUERY = "query.rendicontazione";
	private final String INSERT_QUERY = "query.inseriscirendicontazione";
	private final String UPDATE_QUERY = "query.aggiornarendicontazione";

	/**
	 * Default constructor
	 */
	public ReportDao(Connection conn) {
		super(conn);
	}

	public ReportDao(int idUtente, int idProgetto, Connection conn) {
		super(conn);
		this.idUtente = idUtente;
		this.idProgetto = idProgetto;
	}

	/**
	 * @return ResultSet that represents all days where the user has done the
	 *         report.
	 * @throws SQLException
	 */
	public ResultSet searchMontlyReportForUserAndProject() throws SQLException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String firstDayOfMonth = getStartOfMonth().format(formatter);
		String lastDayOfMonth = getEndOfMonth().format(formatter);
		Statement statement = getConn().createStatement();
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
		queryToExecute.append(whereCondition());
		queryToExecute.append("' and data between '");
		queryToExecute.append(firstDayOfMonth);
		queryToExecute.append("' and '");
		queryToExecute.append(lastDayOfMonth);
		queryToExecute.append("';");
		ResultSet rs = statement.executeQuery(queryToExecute.toString());
		return rs;

	}

	private LocalDate getStartOfMonth() {
		LocalDate today = LocalDate.now();
		LocalDate thisMonth = LocalDate.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
		return thisMonth.with(TemporalAdjusters.firstDayOfMonth());
	}

	private LocalDate getEndOfMonth() {
		LocalDate today = LocalDate.now();
		LocalDate thisMonth = LocalDate.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
		return thisMonth.with(TemporalAdjusters.lastDayOfMonth());
	}

	/**
	 * @return ResultSet that represent a specific day where the user has done the
	 *         report, if he did it. Empty RS otherwise.
	 * @throws SQLException
	 */
	public ResultSet searchSingleDayReportForUserAndProject() throws SQLException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDateTime = this.dataRegistrata.format(formatter);

		Statement statement = getConn().createStatement();
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
		queryToExecute.append(whereCondition());
		queryToExecute.append("' and ");
		queryToExecute.append(" data='");
		queryToExecute.append(formattedDateTime);
		queryToExecute.append("';");
		ResultSet rs = statement.executeQuery(queryToExecute.toString());
		return rs;

	}

	/**
	 * @param report
	 * @throws SQLException
	 * 
	 *             Insert a single day report into db.
	 *
	 */
	public void insertDailyReport(ReportRepository report) throws SQLException {

		StringBuilder updateQuery = new StringBuilder();
		Statement statement = getConn().createStatement();
		updateQuery.append(Config.getInstance().getProperty(INSERT_QUERY));
		updateQuery.append(report.getIdUtente());
		updateQuery.append(",");
		updateQuery.append(report.getIdProgetto());
		updateQuery.append(",'");
		updateQuery.append(report.getData());
		updateQuery.append("',");
		updateQuery.append(report.getNumeroOre());
		updateQuery.append(");");

		statement.executeUpdate(updateQuery.toString());

		statement.close();

	}

	/**
	 * @param report
	 * @throws SQLException
	 * 
	 *             Update the hoursNumber of a single day report that was already
	 *             set.
	 */
	public void updateDailyReport(ReportRepository report) throws SQLException {

		StringBuilder updateQuery = new StringBuilder();
		Statement statement = getConn().createStatement();
		updateQuery.append(Config.getInstance().getProperty(UPDATE_QUERY));
		updateQuery.append(report.getNumeroOre());
		updateQuery.append(whereCondition());
		updateQuery.append("';");

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
		whereConditionToReturn.append(" where id_utente='");
		whereConditionToReturn.append(this.idUtente);
		whereConditionToReturn.append("' and ");
		whereConditionToReturn.append(" id_progetto='");
		whereConditionToReturn.append(this.idProgetto);
		return whereConditionToReturn;
	}

	public LocalDate getDataRegistrata() {
		return dataRegistrata;
	}

	public void setDataRegistrata(LocalDate dataRegistrata) {
		this.dataRegistrata = dataRegistrata;
	}

}
