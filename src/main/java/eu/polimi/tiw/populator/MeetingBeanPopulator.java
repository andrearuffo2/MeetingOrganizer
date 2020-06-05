package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.request.*;

import java.sql.*;
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

    public MeetingBean populateMeeting(ResultSet rs) throws SQLException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingId(rs.getInt(MOConstants.MEETING_ID_DB));
        toReturn.setMeetingTitle(rs.getString(MOConstants.MEETING_TITLE_DB));
        toReturn.setMeetingData(rs.getDate(MOConstants.MEETING_DATE_DB));
        toReturn.setMeetingHour(rs.getTime(MOConstants.MEETING_HOUR_DB));
        toReturn.setMeetingUsernameOrganizator(rs.getString(MOConstants.MEETING_USERNAME_ORGANIZATOR_DB));
        toReturn.setInvolvedEmployeeNumber(rs.getInt(MOConstants.MEETING_INVOLVED_EMPLOYEE_NUMBER_DB));
        toReturn.setMeetingsDuration(rs.getInt(MOConstants.MEETING_DURATION_DB));
        return toReturn;
    }

    public MeetingBean populateMeetingInsert(SaveMeetingRequest request) throws ParseException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingTitle(request.getTitle());
        Date meetingDate = new Date(request.getData().getTime());
        toReturn.setMeetingData(meetingDate);
        DateFormat timeFormat = new SimpleDateFormat("hh:mm");
        Time meetingHour = new Time(timeFormat.parse(request.getHour()).getTime());
        toReturn.setMeetingHour(meetingHour);
        toReturn.setMeetingUsernameOrganizator(request.getMeetingOrganizator());
        toReturn.setInvolvedEmployeeNumber(request.getMembers());
        toReturn.setMeetingsDuration(request.getDuration());
        return toReturn;
    }
}
