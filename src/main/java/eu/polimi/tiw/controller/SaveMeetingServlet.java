package eu.polimi.tiw.controller;

import com.google.gson.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.request.*;
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
@WebServlet("/saveNewMeeting")
public class SaveMeetingServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(SaveMeetingServlet.class);
    RequestDispatcher disp;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.info("SaveMeetingServlet - doPost - START");
        HttpSession session = request.getSession(false);

        FunctionSaveMeetings functionSaveMeetings = new FunctionSaveMeetings();
        EmployeeBean employeeBean = new EmployeeBean();
        HomePageResponse homePageResponse = new HomePageResponse();
        SaveMeetingResponse toReturn = new SaveMeetingResponse();
        FunctionPopulateHomePage functionPopulateHomePage = new FunctionPopulateHomePage();


        List<EmployeeBean> othersEmployeeList = new ArrayList<>();
        List<EmployeeBean> invitedEmployee = new ArrayList<>();

        try {

            log.info("SaveMeetingServlet - doPost - checking sessions...");
            if(session == null
                    || session.getAttribute(MOConstants.SESSION_ATTRIBUTE) == null
                    || session.getAttribute(MOConstants.MEETING_TO_SAVE) == null
                    || session.getAttribute(MOConstants.COMPLETE_EMPLOYEE_LIST) == null){
                throw new SessionExpiredException("Session has exipired please re-login");
            }

            MeetingBean meetingBeanToSave = (MeetingBean) session.getAttribute(MOConstants.MEETING_TO_SAVE);
            List<EmployeeBean> completeEmployeeList = (List<EmployeeBean>) session.getAttribute(MOConstants.COMPLETE_EMPLOYEE_LIST);

            log.info("SaveMeetingServlet - doPost - Initializing request");
            SaveMeetingPopulator saveMeetingPopulatorInstance = SaveMeetingPopulator.getInstance();
            SaveMeetingRequest saveMeetingRequest = new SaveMeetingRequest();

            try {
                 saveMeetingRequest = saveMeetingPopulatorInstance.populateMeetingToInsert(request, meetingBeanToSave);
            } catch (InvalidEmployeeNumberException noEmpEx){
                othersEmployeeList = completeEmployeeList;
                throw new InvalidEmployeeNumberException(noEmpEx.getMessage());
            }

            invitedEmployee = functionSaveMeetings.searchInvitedEmployeesByEmail(saveMeetingRequest.getInvitedEmployeeList());
            othersEmployeeList = saveMeetingPopulatorInstance.fillNotInvitedEmployeeList(saveMeetingRequest.getInvitedEmployeeList(), completeEmployeeList);

            Validator.validateSaveMeetingRequest(saveMeetingRequest, meetingBeanToSave.getInvolvedEmployeeNumber());

            log.info("SaveMeetingServlet - doPost - Searching meeting organizator");
            employeeBean = functionSaveMeetings.searchEmployee(saveMeetingRequest.getMeetingOrganizator());

            log.info("SaveMeetingServlet - doPost - Inserting new meeting");
            int savedMeetingId = functionSaveMeetings.insertNewMeeting(saveMeetingRequest);

            //Add to the list also the meeting organizator to save it into the multi to multi support table
            invitedEmployee.add(employeeBean);

            for (EmployeeBean singleRelatioToSave : invitedEmployee) {
                EmployeeMeetingBean employeeMeetingBean = new EmployeeMeetingBean(singleRelatioToSave.getEmployeeId(), savedMeetingId);
                functionSaveMeetings.insertNewMeetingEmployeesRelations(employeeMeetingBean);
            }

            //Get the newly inserted meeting to return, to populate the table in the homepage
            MeetingBean newlyInsertedMeeting = functionSaveMeetings.searchMeetingById(savedMeetingId);
            if (newlyInsertedMeeting == null) {
                throw new AppCrash("Something went wrong. Please contact the support!");
            }

            //Populating homePage with new datas
            homePageResponse.setEmployeeEmail(employeeBean.getEmail());
            homePageResponse.setEmployeeOwnActiveMeetings(functionPopulateHomePage.searchEmployeeOwnActiveMeetings(employeeBean));
            homePageResponse.setEmployeeInvitedActiveMeetings(functionPopulateHomePage.searchEmployeeInvitedActiveMeetings(employeeBean));
            toReturn.setHomePageResponse(homePageResponse);

            //Setting in session new user homepage data
            session.setAttribute(MOConstants.USER_HOMEPAGE_DATA, homePageResponse);
            emptyUselessSessionAttribute(session);

            request.setAttribute("homePageResponse", homePageResponse);
            log.info("SaveMeetingServlet - doPost - END");

            disp = request.getRequestDispatcher("personalpage.jsp");
            disp.forward(request, response);
        } catch (MaximumNumberOfTryException maxEx) {
            log.error("SaveMeetingServlet - doPost - too many bad attempts");
            emptyUselessSessionAttribute(session);
            disp = request.getRequestDispatcher("cancellation.jsp");
            request.setAttribute("error", maxEx.getMessage());
            disp.forward(request, response);
        } catch (InvalidEmployeeNumberException numExp) {
            log.error("SaveMeetingServlet - doPost - invalid employee number!");
            disp = request.getRequestDispatcher("selectemployee.jsp");
            request.setAttribute(MOConstants.INVITED_EMPLOYEE, invitedEmployee);
            request.setAttribute(MOConstants.OTHERS_EMPLOYEE, othersEmployeeList);
            request.setAttribute("error", numExp.getMessage());
            disp.forward(request, response);
        } catch (SessionExpiredException sesExp) {
            log.error("SaveMeetingServlet - doPost - sessione expired!");
            Validator.resetCounter();
            disp = request.getRequestDispatcher("login.jsp");
            request.setAttribute("error", sesExp.getMessage());
            disp.forward(request, response);
        } catch (CreateMeetingException e) {
            log.error("SaveMeetingServlet - doPost - Something went wrong during meeting save!");
            disp = request.getRequestDispatcher("selectemployee.jsp");
            request.setAttribute(MOConstants.INVITED_EMPLOYEE, invitedEmployee);
            request.setAttribute(MOConstants.OTHERS_EMPLOYEE, othersEmployeeList);
            request.setAttribute("error", e.getMessage());
            disp.forward(request, response);
        } catch (EmployeeNotFoundException ex) {
            log.error("SaveMeetingServlet - doPost - EmployeeNotFoundException!");
            Validator.resetCounter();
            session.invalidate();
            disp = request.getRequestDispatcher("login.jsp");
            request.setAttribute("error", ex.getMessage());
            disp.forward(request, response);
        } catch (ConstraintViolationException throwables) {
            log.error("SaveMeetingServlet - doPost - duplicate meeting exception!");
            Validator.resetCounter();
            disp = request.getRequestDispatcher("personalpage.jsp");
            emptyUselessSessionAttribute(session);
            request.setAttribute("error", throwables.getMessage());
            disp.forward(request, response);
        }catch (SQLException genEx) {
            log.error("SaveMeetingServlet - doPost - something went wrong! See the stacktrace");
            Validator.resetCounter();
            disp = request.getRequestDispatcher("login.jsp");
            request.setAttribute("error", genEx.getMessage());
            disp.forward(request, response);
        } catch (AppCrash e) {
            disp = request.getRequestDispatcher("selectemployee.jsp");
            request.setAttribute(MOConstants.INVITED_EMPLOYEE, invitedEmployee);
            request.setAttribute(MOConstants.OTHERS_EMPLOYEE, othersEmployeeList);
            request.setAttribute("error", e.getMessage());
            disp.forward(request, response);
        }
    }

    public void emptyUselessSessionAttribute(HttpSession session){
        session.removeAttribute(MOConstants.OTHERS_EMPLOYEE);
        session.removeAttribute(MOConstants.INVITED_EMPLOYEE);
        session.removeAttribute(MOConstants.COMPLETE_EMPLOYEE_LIST);
        session.removeAttribute(MOConstants.MEETING_TO_SAVE);
    }
}
