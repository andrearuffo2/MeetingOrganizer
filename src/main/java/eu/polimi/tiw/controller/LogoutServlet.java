package eu.polimi.tiw.controller;

import org.apache.log4j.*;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(LogoutServlet.class);
	RequestDispatcher disp;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("LogoutServlet - doPost - START");

		request.getSession().invalidate();
		disp = request.getRequestDispatcher("login.jsp");
		disp.forward(request, response);
		log.info("LogoutServlet - doPost - END");
	}
}
