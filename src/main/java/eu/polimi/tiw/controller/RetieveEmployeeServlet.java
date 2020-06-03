package eu.polimi.tiw.controller;

import com.google.gson.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/retrieveEmployee")
public class RetieveEmployeeServlet extends GenericServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        FunctionAddMeeting functionAddMeeting = new FunctionAddMeeting();
        try {
            List<EmployeeBean> employeeBeansList = functionAddMeeting.searchAllEmployee();
            response.setContentType("application/json");
            String json = new Gson().toJson(employeeBeansList);
            response.getWriter().write(json);
        } catch (AppCrash | SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(String.valueOf(true));
        }
    }


}
