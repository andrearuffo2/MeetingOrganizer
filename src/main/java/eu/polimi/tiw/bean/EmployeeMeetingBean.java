package eu.polimi.tiw.bean;

public class EmployeeMeetingBean {

    public EmployeeMeetingBean(int employeeId, int meetingId) {
        this.employeeId = employeeId;
        this.meetingId = meetingId;
    }

    public EmployeeMeetingBean() {}

    private int employeeId;
    private int meetingId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }
}
