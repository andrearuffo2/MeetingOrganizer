package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.request.*;

import javax.servlet.http.*;
import java.sql.*;
import java.util.Date;
import java.text.*;

public class MeetingBeanPopulator {
    private static MeetingBeanPopulator instance;

    private MeetingBeanPopulator() {
    }

    public static MeetingBeanPopulator getInstance() {
        if (instance == null) {
            instance = new MeetingBeanPopulator();
        }

        return instance;
    }

    public MeetingBean populateMeeting(ResultSet rs) throws SQLException, ParseException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingId(rs.getInt(MOConstants.MEETING_ID_DB));
        toReturn.setMeetingTitle(rs.getString(MOConstants.MEETING_TITLE_DB));
        Date meetingDate = new Date(rs.getDate(MOConstants.MEETING_DATE_DB).getTime());
        toReturn.setMeetingData(meetingDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String meetingHourToParse = rs.getTime(MOConstants.MEETING_HOUR_DB).toString();
        Time meetingHour = new Time(timeFormat.parse(meetingHourToParse).getTime());
        toReturn.setMeetingHour(meetingHour);
        toReturn.setMeetingUsernameOrganizator(rs.getString(MOConstants.MEETING_USERNAME_ORGANIZATOR_DB));
        toReturn.setInvolvedEmployeeNumber(rs.getInt(MOConstants.MEETING_INVOLVED_EMPLOYEE_NUMBER_DB));
        toReturn.setMeetingsDuration(rs.getInt(MOConstants.MEETING_DURATION_DB));
        return toReturn;
    }

    public MeetingBean populateRequest(HttpServletRequest request) throws ParseException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingTitle(request.getParameter(MOConstants.MEETING_TITLE));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reqMeetingDate = request.getParameter(MOConstants.MEETING_DATE);
        Date meetingDate = dateFormat.parse(reqMeetingDate);
        toReturn.setMeetingData(meetingDate);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String reqMeetingTime = request.getParameter(MOConstants.MEETING_HOUR);
        Time meetingHour = new Time(timeFormat.parse(reqMeetingTime).getTime());
        toReturn.setMeetingHour(meetingHour);

        toReturn.setInvolvedEmployeeNumber(Integer.parseInt(request.getParameter(MOConstants.MEETING_MEMBERS)));
        toReturn.setMeetingsDuration(Integer.parseInt(request.getParameter(MOConstants.MEETING_DURATION)));
        return toReturn;
    }


    public MeetingBean populateMeetingInsert(SaveMeetingRequest request) throws ParseException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingTitle(request.getTitle());
        java.sql.Date meetingDate = new java.sql.Date(request.getData().getTime());
        toReturn.setMeetingData(meetingDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Time meetingHour = new Time(timeFormat.parse(request.getHour()).getTime());
        toReturn.setMeetingHour(meetingHour);
        toReturn.setMeetingUsernameOrganizator(request.getMeetingOrganizator());
        toReturn.setInvolvedEmployeeNumber(request.getMembers());
        toReturn.setMeetingsDuration(request.getDuration());
        return toReturn;
    }
}
