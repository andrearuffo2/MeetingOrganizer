package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.response.*;
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

	private static Logger log = Logger.getLogger(LoginServlet.class);


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		log.info("LoginServlet - doPost - START");
		RequestDispatcher disp;

		// get current session, or initialise one if none
		HttpSession session = request.getSession(true);
		try {
			FunctionLogin functionLogin = new FunctionLogin();
			EmployeeBean employeeBeanForLogin = EmployeeBeanPopulator.populateLogin(request);
			FunctionPopulateHomePage functionPopulateHomePage = new FunctionPopulateHomePage();
			HomePageResponse homePageResponse = new HomePageResponse();
			Validator.validateLogin(employeeBeanForLogin);
			EmployeeBean employeeFound = functionLogin.searchEmployee(employeeBeanForLogin);

			session.setAttribute(MOConstants.SESSION_ATTRIBUTE, request.getParameter(MOConstants.EMAIL));

			homePageResponse.setEmployeeEmail(employeeFound.getEmail());
			homePageResponse.setEmployeeOwnActiveMeetings(functionPopulateHomePage.searchEmployeeOwnActiveMeetings(employeeFound));
			homePageResponse.setEmployeeInvitedActiveMeetings(functionPopulateHomePage.searchEmployeeInvitedActiveMeetings(employeeFound));

			request.setAttribute("homePageResponse", homePageResponse);

			//Setting the homePageResponse to handle errors in the SelectEmployee view
			session.setAttribute(MOConstants.USER_HOMEPAGE_DATA, homePageResponse);
			log.info("LoginServlet - doPost - END");

			disp = request.getRequestDispatcher("personalpage.jsp");
			disp.forward(request, response);

		} catch (BadPasswordException | EmployeeNotFoundException e) {
			log.error("LoginServlet - doPost - employee or password were not correct!");
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", e.getMessage());
			disp.forward(request, response);
		} catch(AppCrash ex){
			log.error("LoginServlet - doPost - something went wrong. Please see the stacktrace!");
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", ex.getMessage());
			disp.forward(request, response);
		}catch (SQLException sqlEx) {
			log.error("LoginServlet - doPost - something went wrong with db connection. Please see the stacktrace!");
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", sqlEx.getMessage());
			disp.forward(request, response);
		}

	}
}
