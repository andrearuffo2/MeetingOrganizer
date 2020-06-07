package eu.polimi.tiw.response;

import eu.polimi.tiw.bean.*;

import java.util.*;

public class HomePageResponse {

    private List<MeetingBean> employeeOwnActiveMeetings;
    private List<MeetingBean> employeeInvitedActiveMeetings;
    private List<EmployeeBean> employeeBeanList;

    public List<MeetingBean> getEmployeeInvitedActiveMeetings() {
        return employeeInvitedActiveMeetings;
    }

    public void setEmployeeInvitedActiveMeetings(List<MeetingBean> employeeInvitedActiveMeetings) {
        this.employeeInvitedActiveMeetings = employeeInvitedActiveMeetings;
    }

    public List<EmployeeBean> getEmployeeBeanList() {
        return employeeBeanList;
    }

    public void setEmployeeBeanList(List<EmployeeBean> employeeBeanList) {
        this.employeeBeanList = employeeBeanList;
    }

    public List<MeetingBean> getEmployeeOwnActiveMeetings() {
        return employeeOwnActiveMeetings;
    }

    public void setEmployeeOwnActiveMeetings(List<MeetingBean> employeeOwnActiveMeetings) {
        this.employeeOwnActiveMeetings = employeeOwnActiveMeetings;
    }
}
