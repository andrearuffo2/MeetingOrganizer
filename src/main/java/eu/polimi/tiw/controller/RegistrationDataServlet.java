package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.businesslogic.FunctionRetrieveRegisterData;
import eu.polimi.tiw.common.AppCrash;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        Represents the controller that returns data to diplay registration
 *        page.
 */
public class RegistrationDataServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher disp;
		try {

			FunctionRetrieveRegisterData functionRetrieveRegisterData = new FunctionRetrieveRegisterData();
			request.setAttribute("projectList", functionRetrieveRegisterData.retrieveProjectsData());
			request.setAttribute("departmentList", functionRetrieveRegisterData.retrieveDepartmentsData());
			disp = request.getRequestDispatcher("registration.jsp");
			disp.forward(request, response);

		} catch (AppCrash e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorapp.jsp");
			disp.forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorsystem.jsp");
			disp.forward(request, response);
		}
	}
}
