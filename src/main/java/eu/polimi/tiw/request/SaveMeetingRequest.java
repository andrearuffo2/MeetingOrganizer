package eu.polimi.tiw.request;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class SaveMeetingRequest {

    private String title;
    private Date data;
    private String hour;
    private int duration;
    private int members;
    private List<String> invitedEmployeeList;
    private String meetingOrganizator;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public List<String> getInvitedEmployeeList() {
        return invitedEmployeeList;
    }

    public void setInvitedEmployeeList(List<String> invitedEmployeeList) {
        this.invitedEmployeeList = invitedEmployeeList;
    }

    public String getMeetingOrganizator() {
        return meetingOrganizator;
    }

    public void setMeetingOrganizator(String meetingOrganizator) {
        this.meetingOrganizator = meetingOrganizator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
