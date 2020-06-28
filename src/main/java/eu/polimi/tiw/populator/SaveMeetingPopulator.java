package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.request.*;

import javax.servlet.http.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

public class SaveMeetingPopulator {
    private static SaveMeetingPopulator instance;

    private SaveMeetingPopulator() {
    }

    public static SaveMeetingPopulator getInstance() {
        if (instance == null) {
            instance = new SaveMeetingPopulator();
        }

        return instance;
    }

    public List<EmployeeBean> fillNotInvitedEmployeeList(List<String> invitedEmployeeList, List<EmployeeBean> completeEmployeeList){
        List<EmployeeBean> toReturn = new ArrayList<>();
        boolean exists = true;

        for(EmployeeBean employee : completeEmployeeList){
            for(String invitedEmployee : invitedEmployeeList){
                if(invitedEmployee.equalsIgnoreCase(employee.getEmail())){
                    exists = false;
                }
            }
            if(exists){
                toReturn.add(employee);
                exists=false;
            }
        }

        return toReturn;
    }

    public SaveMeetingRequest populateMeetingToInsert(HttpServletRequest request, MeetingBean meetingBean) {

        List<String> invitedEmployeeList = Arrays.asList(request.getParameterValues(MOConstants.EMPLOYEE));
        SaveMeetingRequest toReturn = new SaveMeetingRequest();
        toReturn.setTitle(meetingBean.getMeetingTitle());
        toReturn.setData(meetingBean.getMeetingData());
        toReturn.setHour(meetingBean.getMeetingHour().toString());
        toReturn.setDuration(meetingBean.getMeetingsDuration());
        toReturn.setMeetingOrganizator(meetingBean.getMeetingUsernameOrganizator());
        toReturn.setMembers(meetingBean.getInvolvedEmployeeNumber());
        toReturn.setInvitedEmployeeList(invitedEmployeeList);
        return toReturn;
    }
}
