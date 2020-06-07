package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.FunctionLogin;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.validation.Validator;
import org.apache.log4j.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(EmployeeMeetingsServlet.class);

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		log.info("LoginServlet - doPost - START");

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

			log.info("LoginServlet - doPost - END");

		} catch (BadPasswordException | EmployeeNotFoundException e) {
			log.error("LoginServlet - doPost - employee or password were not correct!");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println(e.getMessage());
		} catch(AppCrash ex){
			log.error("LoginServlet - doPost - something went wrong. Please see the stacktrace!");
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().println(ex.getMessage());
		}catch (SQLException sqlEx) {
			log.error("LoginServlet - doPost - something went wrong with db connection. Please see the stacktrace!");
			sqlEx.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().println(sqlEx.getMessage());
		}

	}
}
