package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.validation.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			EmployeeBean employeeBean = EmployeeBeanPopulator.getInstance().populateRegister(request);
			Validator.validateRegistration(employeeBean);
			FunctionRegister functionRegistration = new FunctionRegister();
			boolean employeeAlreadyRegistered = functionRegistration.isEmployeeAlreadyRegistered(employeeBean);
			if(employeeAlreadyRegistered){
				throw new AppCrash("An employee with this email is already registered");
			}

			//Check if user already exists
			if(functionRegistration.isEmployeeAlreadyRegistered(employeeBean)){
				throw new AppCrash("User already exists");
			}

			functionRegistration.register(employeeBean);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (AppCrash e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write(e.getMessage());
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("Something went wrong please retry later!");
		}
	}

}
