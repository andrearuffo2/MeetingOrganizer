package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.FunctionLogin;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.validation.Validator;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/login")
@MultipartConfig
public class LoginServlet extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		try {
			FunctionLogin functionLogin = new FunctionLogin();
			EmployeeBean employeeBeanForLogin = EmployeeBeanPopulator.populateLogin(request);
			Validator.validateLogin(employeeBeanForLogin);
			EmployeeBean employeeFound = functionLogin.searchEmployee(employeeBeanForLogin);

			request.getSession().setAttribute(MOConstants.SESSION_ATTRIBUTE, employeeFound);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(employeeFound.getEmail());

		} catch (BadPasswordException | EmployeeNotFoundException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println(e.getMessage());
		} catch(AppCrash ex){
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println(ex.getMessage());
		}catch (SQLException sqlEx) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println(sqlEx.getMessage());
		}

	}
}
