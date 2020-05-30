package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.PersonalPageBean;
import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.businesslogic.FunctionLogin;
import eu.polimi.tiw.businesslogic.FunctionRetrieveRegisterData;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.RegisterBeanPopulator;
import eu.polimi.tiw.repository.ProjectRepository;

public class LoginServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;
	private static final String COOKIE_NAME = "user";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {
			RegisterBean regBean = RegisterBeanPopulator.populateLogin(request);
			FunctionLogin functionLogin = new FunctionLogin();
			FunctionRetrieveRegisterData functionRetrieveRegisterData = new FunctionRetrieveRegisterData();
			functionLogin.searchUser(regBean);
			List<PersonalPageBean> searchUsersProjects = functionLogin.searchUsersProjects(regBean);
			List<ProjectRepository> retrieveProjectsDataForLoginPage = functionRetrieveRegisterData
					.retrieveProjectsDataForLoginPage(searchUsersProjects);

			boolean isPresentCookie = false;

			if (request.getCookies() != null || request.getCookies().length != 0) {
				for (Cookie cookie : request.getCookies()) {
					if (COOKIE_NAME.equalsIgnoreCase(cookie.getName())) {
						isPresentCookie = true;
						response.addCookie(cookie);
					} else {
						response.addCookie(cookie);
					}
				}

				if (!isPresentCookie) {
					Cookie loginCookie = new Cookie("user", String.valueOf(regBean.getIdUtente()));
					// setting cookie to expiry in 30 mins
					loginCookie.setMaxAge(30 * 60);
					response.addCookie(loginCookie);
				}
			}

			request.setAttribute("usersprojects", searchUsersProjects);
			request.setAttribute("projectList", retrieveProjectsDataForLoginPage);
			disp = request.getRequestDispatcher("personalpage.jsp");
			disp.forward(request, response);

		} catch (AppCrash e) {
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", e.getMessage());
			disp.include(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorsystem.jsp");
			disp.forward(request, response);
		}
	}
}
