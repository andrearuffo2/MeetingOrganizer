package eu.polimi.tiw.controller;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.response.*;
import eu.polimi.tiw.validation.*;
import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/homepage")
@MultipartConfig
public class HomePageServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(HomePageServlet.class);


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		log.info("HomePageServlet - doPost - START");
		RequestDispatcher disp;

		// get current session, or initialise one if none
		HttpSession session = request.getSession(true);
		try {
			if(session == null
					|| session.getAttribute(MOConstants.SESSION_ATTRIBUTE) == null
					|| session.getAttribute(MOConstants.USER_HOMEPAGE_DATA) == null){
				throw new SessionExpiredException("Session has exipired please re-login");
			}

			Validator.resetCounter();
			HomePageResponse homePageResponse = (HomePageResponse) session.getAttribute(MOConstants.USER_HOMEPAGE_DATA);
			request.setAttribute("homePageResponse", homePageResponse);

			log.info("HomePageServlet - doPost - END");
			disp = request.getRequestDispatcher("personalpage.jsp");
			disp.forward(request, response);

		} catch (SessionExpiredException sesExp) {
			log.error("SaveMeetingServlet - doPost - sessione expired!");
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", sesExp.getMessage());
			disp.forward(request, response);
		}
	}
}
