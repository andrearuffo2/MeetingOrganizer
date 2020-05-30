package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.polimi.tiw.bean.ReportBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.Config;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.dao.ReportDao;
import eu.polimi.tiw.populator.ReportBeanPopulator;
import eu.polimi.tiw.repository.ReportRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle the storage of report information for
 *        users.
 */
public class FunctionReport {

	/**
	 * @param reportBean
	 * @throws SQLException
	 */
	private int isAlreadyBeenReported(ReportBean reportBean) throws SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ReportDao reportDao = new ReportDao(reportBean.getUserId(), reportBean.getProjectId(), conn);
		reportDao.setDataRegistrata(reportBean.getReportDay());

		ResultSet rs = reportDao.searchSingleDayReportForUserAndProject();
		if (rs.next())
			return rs.getInt("numero_ore");
		return 0;

	}

	/**
	 * @param reportBean
	 * @throws SQLException
	 * @throws AppCrash
	 */
	public void saveOrUpdate(ReportBean reportBean) throws SQLException, AppCrash {

		if (!validateInput(reportBean)) {
			throw new AppCrash("Hai già rendicontato il numero massimo di ore per il progetto "
					+ reportBean.getProjectId() + " questo mese");
		}

		if (isAlreadyBeenReported(reportBean) != 0) {
			updateReportForUser(reportBean);
		} else {
			saveReportForUser(reportBean);
		}

	}

	/**
	 * @param registerBean
	 * @param conn
	 * @throws SQLException
	 */
	private void saveReportForUser(ReportBean reportBean) throws SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ReportDao reportDao = new ReportDao(conn);

		ReportRepository repository = ReportBeanPopulator.convertReport(reportBean);
		reportDao.insertDailyReport(repository);
	}

	private boolean validateInput(ReportBean reportBean) throws SQLException {

		String numOreMax = Config.getInstance().getProperty("maxoreprogetto");

		// Se la giornata scelta è stata già rendicontata e voglio aggiornarla, allora
		// la sottraggo dal monte ore mensile,
		// e aggiungo il nuovo monte ore per verificare che questo non ecceda con il
		// limite mensile presente in configurazione (120h).
		int alreadyBeenReportedDailyHour = isAlreadyBeenReported(reportBean);
		int alreadyReportedHours = getMonthlyTotalHours(reportBean);
		if (alreadyBeenReportedDailyHour != 0) {
			alreadyReportedHours -= alreadyBeenReportedDailyHour;
		}
		alreadyReportedHours += reportBean.getHour();
		if (Integer.parseInt(numOreMax) >= alreadyReportedHours) {
			return true;
		}
		return false;

	}

	private int getMonthlyTotalHours(ReportBean reportBean) throws SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ReportDao reportDao = new ReportDao(reportBean.getUserId(), reportBean.getProjectId(), conn);
		int totalHourNumber = 0;

		ResultSet rs = reportDao.searchMontlyReportForUserAndProject();
		while (rs.next()) {
			totalHourNumber += rs.getInt("numero_ore");
		}

		return totalHourNumber;

	}

	/**
	 * @param registerBean
	 * @param conn
	 * @throws SQLException
	 */
	private void updateReportForUser(ReportBean reportBean) throws SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ReportDao reportDao = new ReportDao(reportBean.getUserId(), reportBean.getProjectId(), conn);

		ReportRepository repository = ReportBeanPopulator.convertReport(reportBean);
		reportDao.updateDailyReport(repository);
	}
}
