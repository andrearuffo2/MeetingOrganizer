package eu.polimi.tiw.response;

import eu.polimi.tiw.bean.*;

import java.util.*;

public class HomePageResponse {

    private String employeeEmail;
    private List<MeetingBean> employeeOwnActiveMeetings;
    private List<MeetingBean> employeeInvitedActiveMeetings;

    public List<MeetingBean> getEmployeeInvitedActiveMeetings() {
        return employeeInvitedActiveMeetings;
    }

    public void setEmployeeInvitedActiveMeetings(List<MeetingBean> employeeInvitedActiveMeetings) {
        this.employeeInvitedActiveMeetings = employeeInvitedActiveMeetings;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public List<MeetingBean> getEmployeeOwnActiveMeetings() {
        return employeeOwnActiveMeetings;
    }

    public void setEmployeeOwnActiveMeetings(List<MeetingBean> employeeOwnActiveMeetings) {
        this.employeeOwnActiveMeetings = employeeOwnActiveMeetings;
    }
}
