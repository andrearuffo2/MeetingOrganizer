package eu.polimi.tiw.controller;

import com.google.gson.*;
import com.mysql.jdbc.exceptions.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.businesslogic.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.request.*;

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
@WebServlet("/saveNewMeeting")
public class SaveMeetingServlet extends AbstractServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Gson requestJson = new Gson();
        SaveMeetingRequest saveMeetingRequest = requestJson.fromJson(getBody(request), SaveMeetingRequest.class);
        MeetingBean newlyInsertedMeeting = new MeetingBean();
        FunctionSaveMeetings functionSaveMeetings = new FunctionSaveMeetings();
        List<EmployeeBean> invitedEmployee = new ArrayList<>();
        EmployeeBean employeeBean = new EmployeeBean();
        try {

            //verify that sessione has not expired
            if (request.getSession() == null || request.getSession().getAttribute(MOConstants.SESSION_ATTRIBUTE) == null) {
                throw new SessionExpiredException("");
            }

            //Searching meeting organizator
            employeeBean = functionSaveMeetings.searchEmployee(saveMeetingRequest.getMeetingOrganizator());
            int savedMeetingId = functionSaveMeetings.insertNewMeeting(saveMeetingRequest);
            invitedEmployee = functionSaveMeetings.searchInvitedEmployeesByEmail(saveMeetingRequest.getInvitedEmployeeList());

            //Add to the list also the meeting organizator to save it into the multi to multi support table
            invitedEmployee.add(employeeBean);

            for (EmployeeBean singleRelatioToSave : invitedEmployee) {
                EmployeeMeetingBean employeeMeetingBean = new EmployeeMeetingBean(singleRelatioToSave.getEmployeeId(), savedMeetingId);
                functionSaveMeetings.insertNewMeetingEmployeesRelations(employeeMeetingBean);
            }

            //Get the newly inserted meeting to return, to populate the table in the homepage
            newlyInsertedMeeting = functionSaveMeetings.searchMeetingById(savedMeetingId);
            if (newlyInsertedMeeting == null) {
                throw new AppCrash("Something went wrong. Please contact the support!");
            }

            response.setContentType("application/json");
            String json = new Gson().toJson(newlyInsertedMeeting);
            response.getWriter().write(json);

        } catch (SessionExpiredException sesExp) {
            ErrorBean errorBean = new ErrorBean(MOConstants.SESSION_EXPIRED_MESSAGE);
            String errorBeanJson = new Gson().toJson(errorBean);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(errorBeanJson);
        } catch (CreateMeetingException e) {
            ErrorBean errorBean = new ErrorBean(e.getMessage());
            String errorBeanJson = new Gson().toJson(errorBean);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            response.getWriter().write(errorBeanJson);
        } catch (EmployeeNotFoundException ex) {
            ErrorBean errorBean = new ErrorBean(ex.getMessage());
            String errorBeanJson = new Gson().toJson(errorBean);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(errorBeanJson);
        } catch (ConstraintViolationException throwables) {
            ErrorBean errorBean = new ErrorBean(throwables.getMessage());
            String errorBeanJson = new Gson().toJson(errorBean);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(errorBeanJson);
        }catch (AppCrash | SQLException genEx) {
            ErrorBean errorBean = new ErrorBean(genEx.getMessage());
            String errorBeanJson = new Gson().toJson(errorBean);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write(errorBeanJson);
        }

    }

    public static String getBody(HttpServletRequest request)  {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            // throw ex;
            return "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {

                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
