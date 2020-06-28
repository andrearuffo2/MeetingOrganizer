package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.validation.*;
import org.apache.log4j.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/register")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(RegisterServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("RegisterServlet - doPost - START");
		RequestDispatcher disp;
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
			log.info("RegisterServlet - doPost - END");
			disp = request.getRequestDispatcher("registrationsuccessfull.jsp");
			disp.forward(request, response);
		} catch (AppCrash e) {
			log.error("RegisterServlet - doPost - Something went wrong! Please see the stacktrace");
			disp = request.getRequestDispatcher("registration.jsp");
			request.setAttribute("error", e.getMessage());
			disp.include(request, response);
		} catch (SQLException sqlEx) {
			log.error("RegisterServlet - doPost - Something went wrong with db connection! Please see the stacktrace");
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", sqlEx.getMessage());
			disp.forward(request, response);
		}
	}

}
