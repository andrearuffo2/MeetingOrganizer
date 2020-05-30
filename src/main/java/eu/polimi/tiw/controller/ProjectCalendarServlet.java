package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.ProjectCalendarBean;
import eu.polimi.tiw.businesslogic.FunctionRetrieveCalendarForProject;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.ProjectCalendarBeanPopulator;

public class ProjectCalendarServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {

			ProjectCalendarBean projectBean = ProjectCalendarBeanPopulator.getInstance().populate(request);
			FunctionRetrieveCalendarForProject function = new FunctionRetrieveCalendarForProject();
			String projectName = function.retrieveProjectName(projectBean);
			List<ProjectCalendarBean> userProjectCurrentReport = function.retriveUserProjectCurrentReport(projectBean);
			request.setAttribute("projectName", projectName);
			request.setAttribute("reportMonth", projectBean.getCurrentMonth());
			request.setAttribute("reportYear", projectBean.getCurrentYear());
			request.setAttribute("daysList", userProjectCurrentReport);

			disp = request.getRequestDispatcher("reportcalendar.jsp");
			disp.forward(request, response);

		} catch (AppCrash e) {
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("errorMessage", e.getMessage());
			disp.include(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorsystem.jsp");
			disp.forward(request, response);
		}
	}
}
