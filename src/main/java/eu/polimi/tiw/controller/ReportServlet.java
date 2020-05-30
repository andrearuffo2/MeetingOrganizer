package eu.polimi.tiw.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.polimi.tiw.bean.ReportBean;
import eu.polimi.tiw.businesslogic.FunctionReport;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.populator.ReportBeanPopulator;

public class ReportServlet extends GenericServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher disp;
		try {
			List<ReportBean> reportList = ReportBeanPopulator.populateReport(request);
			FunctionReport reportFunction = new FunctionReport();

			for (ReportBean reportBean : reportList) {
				reportFunction.saveOrUpdate(reportBean);
			}

			// implmentare logica per salvataggio progetto associato allo user in oggetto.
			// da fare in una servlet apposita.

			disp = request.getRequestDispatcher("reportsuccessfull.jsp");
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
