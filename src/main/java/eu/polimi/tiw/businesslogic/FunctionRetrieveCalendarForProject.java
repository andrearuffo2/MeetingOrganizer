package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import eu.polimi.tiw.bean.ProjectCalendarBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.dao.ProjectDao;
import eu.polimi.tiw.dao.ReportDao;
import eu.polimi.tiw.populator.ProjectCalendarBeanPopulator;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class implements the logic to retrieve data's for
 *        specific project report calendar.
 */
public class FunctionRetrieveCalendarForProject {

	/**
	 * @param projectCalendarBean
	 * @return List of data to populate the calendar
	 * @throws SQLException
	 */
	public List<ProjectCalendarBean> retriveUserProjectCurrentReport(ProjectCalendarBean projectCalendarBean)
			throws SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ReportDao reportDao = new ReportDao(projectCalendarBean.getUserId(), projectCalendarBean.getProjectId(), conn);

		ResultSet projectReportMonthlyData = reportDao.searchMontlyReportForUserAndProject();

		List<ProjectCalendarBean> toReturn = ProjectCalendarBeanPopulator.getInstance()
				.convert(projectReportMonthlyData);

		projectReportMonthlyData.close();
		conn.close();
		return toReturn;

	}

	/**
	 * @param projectCalendarBean
	 * @return the project name
	 * @throws SQLException
	 * @throws AppCrash
	 */
	public String retrieveProjectName(ProjectCalendarBean projectCalendarBean) throws SQLException, AppCrash {
		Connection conn = DbConnection.getInstance().getConnection();
		ProjectDao projectDao = new ProjectDao(projectCalendarBean.getProjectId(), conn);
		String toReturn = "";

		// It will return single result
		ResultSet singleProject = projectDao.searchSingleProject();

		// setting the projectName
		if (!singleProject.next()) {
			throw new AppCrash("Somenthing went wrong. No project found. Please contact system administrator");
		}

		toReturn = singleProject.getString("nome");
		singleProject.close();
		conn.close();
		return toReturn;
	}

}
