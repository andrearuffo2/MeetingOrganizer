package eu.polimi.tiw.populator;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import eu.polimi.tiw.bean.ProjectCalendarBean;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class populate the ProjectCalendarBean from the HttpRequest and
 *        saves fields from the ResultSet.
 */
public class ProjectCalendarBeanPopulator {

	private static ProjectCalendarBeanPopulator instance;

	private ProjectCalendarBeanPopulator() {
	}

	// Lazy initialization singleton pattern
	public static ProjectCalendarBeanPopulator getInstance() {
		if (instance == null) {
			instance = new ProjectCalendarBeanPopulator();
		}
		return instance;
	}

	/**
	 * @param req
	 * @return Populated ProjectCalendarBean
	 */
	public ProjectCalendarBean populate(HttpServletRequest req) {

		ProjectCalendarBean beanToReturn = new ProjectCalendarBean();
		beanToReturn.setProjectId(Integer.parseInt(req.getParameter("projectCalendar")));
		beanToReturn.setUserId(Integer.parseInt(req.getParameter("userId")));

		return beanToReturn;

	}

	/**
	 * @param reportRs
	 * @return List<ProjectCalendarBean> retrived from the db
	 * @throws SQLException
	 */
	public List<ProjectCalendarBean> convert(ResultSet reportRs) throws SQLException {

		List<ProjectCalendarBean> toReturn = new ArrayList<>();

		while (reportRs.next()) {
			ProjectCalendarBean singleDayToAdd = new ProjectCalendarBean();
			Date date = reportRs.getDate("data");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			singleDayToAdd.setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			singleDayToAdd.setHourNumber(reportRs.getInt("numero_ore"));
			toReturn.add(singleDayToAdd);
		}

		Collections.sort(toReturn);
		return toReturn;

	}

}
