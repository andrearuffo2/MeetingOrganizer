package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.FunctionLogin;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.*;
/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/login")
public class LoginServlet extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher disp;
		try {
			FunctionLogin functionLogin = new FunctionLogin();
			EmployeeBean employeeBean = functionLogin.searchEmployee(EmployeeBeanPopulator.populateLogin(request));
			List<MeetingBean> employeeOwnActiveMeetings = functionLogin.searchEmployeeOwnActiveMeetings(employeeBean);
			List<MeetingBean> employeeInvitedActiveMeetings = functionLogin.searchEmployeeInvitedActiveMeetings(employeeBean);
			boolean isPresentCookie = false;
			if (request.getCookies() != null || request.getCookies().length != 0) {
				Cookie[] var8 = request.getCookies();
				int var9 = var8.length;
				int var10 = 0;

				while(true) {
					if (var10 >= var9) {
						if (!isPresentCookie) {
							Cookie loginCookie = new Cookie("user", String.valueOf(employeeBean.getEmployeeId()));
							loginCookie.setMaxAge(1800);
							response.addCookie(loginCookie);
						}
						break;
					}

					Cookie cookie = var8[var10];
					if ("user".equalsIgnoreCase(cookie.getName())) {
						isPresentCookie = true;
						response.addCookie(cookie);
					} else {
						response.addCookie(cookie);
					}

					++var10;
				}
			}

			//Meetings list made by him
			request.setAttribute("meetingsOwnList", employeeOwnActiveMeetings);
			request.setAttribute("employeeData", employeeBean);
			//Meetings list were the employee was invited
			request.setAttribute("meetingsInvitedList", employeeInvitedActiveMeetings);
			disp = request.getRequestDispatcher("personalpage2.jsp");
			disp.forward(request, response);
		} catch (AppCrash var12) {
			disp = request.getRequestDispatcher("login.jsp");
			request.setAttribute("error", var12.getMessage());
			disp.include(request, response);
		} catch (SQLException var13) {
			request.setAttribute("errorMessage", var13.getMessage());
			disp = request.getRequestDispatcher("errorsystem.jsp");
			disp.forward(request, response);
		}

	}
}
