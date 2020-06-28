package eu.polimi.tiw.bean;

import java.sql.*;
import java.util.Date;

public class MeetingBean {
    private int meetingId;
    private String meetingTitle;
    private Date meetingData;
    private Time meetingHour;
    private int meetingDuration;
    private int involvedEmployeeNumber;
    private String meetingUsernameOrganizator;

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

    public Time getMeetingHour() {
        return this.meetingHour;
    }

    public void setMeetingHour(Time meetingHour) {
        this.meetingHour = meetingHour;
    }

    public int getMeetingsDuration() {
        return this.meetingDuration;
    }

    public void setMeetingsDuration(int meetingsDuration) {
        this.meetingDuration = meetingsDuration;
    }

    public int getInvolvedEmployeeNumber() {
        return this.involvedEmployeeNumber;
    }

    public void setInvolvedEmployeeNumber(int involvedEmployeeNumber) {
        this.involvedEmployeeNumber = involvedEmployeeNumber;
    }

    public String getMeetingUsernameOrganizator() {
        return meetingUsernameOrganizator;
    }

    public void setMeetingUsernameOrganizator(String meetingUsernameOrganizator) {
        this.meetingUsernameOrganizator = meetingUsernameOrganizator;
    }

    public int getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(int meetingDuration) {
        this.meetingDuration = meetingDuration;
    }
}
