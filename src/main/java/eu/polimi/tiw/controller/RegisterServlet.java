package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.businesslogic.FunctionRegister;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.RegisterBeanPopulator;

public class RegisterServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {
			RegisterBean regBean = RegisterBeanPopulator.populateRegister(request);
			FunctionRegister functionRegistration = new FunctionRegister();
			functionRegistration.register(regBean);
			disp = request.getRequestDispatcher("registrationsuccessfull.jsp");
			disp.forward(request, response);

		} catch (AppCrash e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorapp.jsp");
			disp.forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			disp = request.getRequestDispatcher("errorsystem.jsp");
			disp.forward(request, response);
		}
	}

}
