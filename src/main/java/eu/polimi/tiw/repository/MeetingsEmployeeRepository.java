package eu.polimi.tiw.repository;

public class MeetingsEmployeeRepository {
    private int meetingsId;
    private int employeeId;

    public MeetingsEmployeeRepository() {
    }

    public int getMeetingsId() {
        return this.meetingsId;
    }

    public void setMeetingsId(int meetingsId) {
        this.meetingsId = meetingsId;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
