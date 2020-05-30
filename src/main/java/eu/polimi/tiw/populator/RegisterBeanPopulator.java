
package eu.polimi.tiw.populator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.common.AppCrash;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPHOSHOT
 */
public class RegisterBeanPopulator {

	public static RegisterBean populateRegister(HttpServletRequest req) throws AppCrash {

		String departmentData = req.getParameter("selectedValueDip");
		String projectData = req.getParameter("selectedValuePrj");
		String[] splittedDepartmentData = departmentData.split("-");
		String[] splittedProjectData = projectData.split("-");

		RegisterBean toPopulate = new RegisterBean();
		toPopulate.setName(req.getParameter("name"));
		toPopulate.setSurname(req.getParameter("surname"));
		toPopulate.setEmail(req.getParameter("email"));
		toPopulate.setPassword(req.getParameter("psw"));
		toPopulate.setIdDipartimento(Integer.parseInt(splittedDepartmentData[0].trim()));
		toPopulate.setDipartimento(splittedDepartmentData[1].trim());
		toPopulate.setIdProgetto(Integer.parseInt(splittedProjectData[0].trim()));
		toPopulate.setProgetto(splittedProjectData[1].trim());

		return toPopulate;

	}

	public static RegisterBean populateLogin(HttpServletRequest req) throws AppCrash {

		RegisterBean toPopulate = new RegisterBean();
		toPopulate.setEmail(req.getParameter("email"));
		toPopulate.setPassword(req.getParameter("psw"));

		if (StringUtils.isNotEmpty(req.getParameter("refresh"))) {
			toPopulate.setIdUtente(Integer.parseInt(req.getParameter("userId")));
			toPopulate.setRefreshPage(true);
		}

		return toPopulate;

	}

}
