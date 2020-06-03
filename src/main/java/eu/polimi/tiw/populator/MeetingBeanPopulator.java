package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;

import java.sql.*;

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

    public MeetingBean populate(ResultSet rs) throws SQLException {
        MeetingBean toReturn = new MeetingBean();
        toReturn.setMeetingId(rs.getInt(MOConstants.MEETING_ID));
        toReturn.setMeetingTitle(rs.getString(MOConstants.MEETING_TITLE));
        toReturn.setMeetingData(rs.getDate(MOConstants.MEETING_DATE));
        toReturn.setMeetingHour(rs.getTime(MOConstants.MEETING_HOUR));
        toReturn.setEmployeeUsername(rs.getString(MOConstants.MEETING_USERNAME_ORGANIZER));
        toReturn.setInvolvedEmployeeNumber(rs.getInt(MOConstants.MEETING_INVOLVED_EMPLOYEE_NUMBER));
        toReturn.setMeetingsDuration(rs.getInt(MOConstants.MEETING_DURATION));
        return toReturn;
    }
}
