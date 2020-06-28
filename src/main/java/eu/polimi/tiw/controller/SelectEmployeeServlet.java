package eu.polimi.tiw.controller;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.response.*;
import eu.polimi.tiw.validation.*;
import org.apache.log4j.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 *
 */
@WebServlet("/selectMeetingEmployee")
public class SelectEmployeeServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(SelectEmployeeServlet.class);
    RequestDispatcher disp;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.info("SelectEmployeeServlet - doPost - START");
        FunctionPopulateHomePage functionPopulateHomePage = new FunctionPopulateHomePage();
        HttpSession session = request.getSession(false);
        try {

            log.info("SelectEmployeeServlet - doPost - checking sessions...");
            if(session == null || session.getAttribute(MOConstants.SESSION_ATTRIBUTE) == null){
                throw new SessionExpiredException("Session has exipired please re-login");
            }

            String loggedEmployeeEmail = (String) session.getAttribute(MOConstants.SESSION_ATTRIBUTE);
            MeetingBean meetingBean = MeetingBeanPopulator.getInstance().populateRequest(request);
            meetingBean.setMeetingUsernameOrganizator(loggedEmployeeEmail);
            Validator.validateMeetingInsert(meetingBean);
            List<EmployeeBean> selectEmployeeResponse = functionPopulateHomePage.searchAllEmployee();
            selectEmployeeResponse = functionPopulateHomePage.removeLoggedEmployeeFromEmployeeList(selectEmployeeResponse, loggedEmployeeEmail);

            //Saving meetingBean into session to handle selectEmployeeView before save the meeting to the db
            session.setAttribute(MOConstants.MEETING_TO_SAVE, meetingBean);
            session.setAttribute(MOConstants.COMPLETE_EMPLOYEE_LIST, selectEmployeeResponse);

            request.setAttribute("selectEmployeeResponse", selectEmployeeResponse);
            log.info("SelectEmployeeServlet - doPost - END");

            disp = request.getRequestDispatcher("selectemployee.jsp");
            disp.forward(request, response);

        } catch (ParseException e) {
            //Returns the homepageResponse from login to correctly refresh page
            disp = request.getRequestDispatcher("personalpage.jsp");
            HomePageResponse hpResponse = (HomePageResponse)session.getAttribute(MOConstants.USER_HOMEPAGE_DATA);
            request.setAttribute(MOConstants.USER_HOMEPAGE_DATA, hpResponse);
            request.setAttribute("error", e.getMessage());
            disp.forward(request, response);
        }catch (SessionExpiredException sessionEx){
            disp = request.getRequestDispatcher("login.jsp");
            request.setAttribute("error", sessionEx.getMessage());
            disp.forward(request, response);
        } catch (AppCrash ex){
            disp = request.getRequestDispatcher("personalpage.jsp");
            HomePageResponse hpResponse = (HomePageResponse)session.getAttribute(MOConstants.USER_HOMEPAGE_DATA);
            request.setAttribute(MOConstants.USER_HOMEPAGE_DATA, hpResponse);
            request.setAttribute("error", ex.getMessage());
            disp.forward(request, response);
        } catch (SQLException sqlEx){
            disp = request.getRequestDispatcher("personalpage.jsp");
            HomePageResponse hpResponse = (HomePageResponse)session.getAttribute(MOConstants.USER_HOMEPAGE_DATA);
            request.setAttribute(MOConstants.USER_HOMEPAGE_DATA, hpResponse);
            request.setAttribute("error", sqlEx.getMessage());
            disp.forward(request, response);
        }
    }
}
