
package eu.polimi.tiw.populator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import eu.polimi.tiw.bean.ReportBean;
import eu.polimi.tiw.repository.ReportRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPHOSHOT
 */
public class ReportBeanPopulator {

	public static List<ReportBean> populateReport(HttpServletRequest req) {

		List<ReportBean> toPopulate = new ArrayList<>();
		int projectsNumber = Integer.parseInt(req.getParameter("projectsNumber")) + 1;
		for (int i = 0; i < projectsNumber; i++) {
			ReportBean singleReport = new ReportBean();
			singleReport.setHour(Integer.parseInt(req.getParameter("hourNumber" + String.valueOf(i))));
			singleReport.setProjectId(Integer.parseInt(req.getParameter("projectName" + String.valueOf(i))));
			singleReport.setUserId(Integer.parseInt(req.getParameter("userId")));

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formatter = formatter.withLocale(Locale.ITALIAN);
			LocalDate date = LocalDate.parse(req.getParameter("dayproject" + String.valueOf(i)), formatter);
			singleReport.setReportDay(date);
			toPopulate.add(singleReport);
		}

		return toPopulate;

	}

	public static ReportRepository convertReport(ReportBean reportBean) {

		ReportRepository toReturn = new ReportRepository();

		toReturn.setData(reportBean.getReportDay());
		toReturn.setIdUtente(reportBean.getUserId());
		toReturn.setIdProgetto(reportBean.getProjectId());
		toReturn.setNumeroOre(reportBean.getHour());

		return toReturn;

	}

}
