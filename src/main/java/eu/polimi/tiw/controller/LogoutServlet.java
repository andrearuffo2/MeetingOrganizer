package eu.polimi.tiw.controller;

import org.apache.log4j.*;

import java.io.IOException;

import javax.servlet.ServletException;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("LogoutServlet - doPost - START");

		request.getSession().invalidate();

		log.info("LogoutServlet - doPost - END");

	}
}
