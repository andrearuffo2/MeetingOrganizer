package eu.polimi.tiw.bean;

import java.util.Date;

public class MeetingBean {
    private int meetingId;
    private String meetingTitle;
    private Date meetingData;
    private Date meetingHour;
    private int meetingsDuration;
    private int involvedEmployeeNumber;
    private String employeeUsername;

    public MeetingBean() {
    }

    public int getMeetingId() {
        return this.meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingTitle() {
        return this.meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public Date getMeetingData() {
        return this.meetingData;
    }

    public void setMeetingData(Date meetingData) {
        this.meetingData = meetingData;
    }

    public Date getMeetingHour() {
        return this.meetingHour;
    }

    public void setMeetingHour(Date meetingHour) {
        this.meetingHour = meetingHour;
    }

    public int getMeetingsDuration() {
        return this.meetingsDuration;
    }

    public void setMeetingsDuration(int meetingsDuration) {
        this.meetingsDuration = meetingsDuration;
    }

    public int getInvolvedEmployeeNumber() {
        return this.involvedEmployeeNumber;
    }

    public void setInvolvedEmployeeNumber(int involvedEmployeeNumber) {
        this.involvedEmployeeNumber = involvedEmployeeNumber;
    }

    public String getEmployeeUsername() {
        return this.employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
}
