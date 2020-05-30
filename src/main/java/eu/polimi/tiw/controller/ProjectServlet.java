package eu.polimi.tiw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProjectServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> projectList = new ArrayList<>();
		request.setAttribute("projectList", projectList);
		RequestDispatcher disp = request.getRequestDispatcher("projectselection.jsp");
		disp.forward(request, response);
	}

}
