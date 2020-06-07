package eu.polimi.tiw.controller;

import com.google.gson.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.response.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/retrieveMeetingsData")
public class EmployeeMeetingsServlet extends AbstractServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        EmployeeBean employeeBean = new EmployeeBean();
        FunctionPopulateHomePage functionPopulateHomePage = new FunctionPopulateHomePage();
        HomePageResponse homePageResponse = new HomePageResponse();

        try {

            employeeBean = functionPopulateHomePage.searchEmployee(request.getParameter(MOConstants.EMAIL));

            homePageResponse.setEmployeeOwnActiveMeetings(functionPopulateHomePage.searchEmployeeOwnActiveMeetings(employeeBean));
            homePageResponse.setEmployeeInvitedActiveMeetings(functionPopulateHomePage.searchEmployeeInvitedActiveMeetings(employeeBean));
            homePageResponse.setEmployeeBeanList(functionPopulateHomePage.searchAllEmployee());

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            String json = new Gson().toJson(homePageResponse);
            response.getWriter().write(json);

        } catch (EmployeeNotFoundException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(ex.getMessage());
        } catch (AppCrash | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(e.getMessage());
        }
    }

}
