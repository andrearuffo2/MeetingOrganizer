package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.populator.*;

@WebServlet("/register")
public class RegisterServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {
			EmployeeBean employeeBean = EmployeeBeanPopulator.populateRegister(request);
			FunctionRegister functionRegistration = new FunctionRegister();
			functionRegistration.register(employeeBean);
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EmployeeBean employeeBean = new EmployeeBean();
		employeeBean.setEmail(request.getParameter(MOConstants.EMAIL));
		FunctionRegister registerFunction = new FunctionRegister();
		try {
			boolean employeeAlreadyRegistered = registerFunction.isEmployeeAlreadyRegistered(employeeBean);
			response.setContentType("application/json");
			response.getWriter().write(String.valueOf(employeeAlreadyRegistered));
		} catch (AppCrash | SQLException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("application/json");
			response.getWriter().write(String.valueOf(true));
		}
	}
}
