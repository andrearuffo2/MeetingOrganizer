package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.ProjectUserBean;
import eu.polimi.tiw.businesslogic.FunctionAddProject;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.ProjectUserPopulator;

public class ProjectAdditionServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {

			ProjectUserBean projectUserToAdd = ProjectUserPopulator.getInstance().populate(request);
			FunctionAddProject function = new FunctionAddProject();
			function.registerUserProject(projectUserToAdd);
			disp = request.getRequestDispatcher("projectaddsuccessfull.jsp");
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
