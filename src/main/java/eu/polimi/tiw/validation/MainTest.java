package eu.polimi.tiw.validation;

import eu.polimi.tiw.common.*;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

public class MainTest {

    public static void main(String[] args) throws ParseException {

        Date todaysDate = Calendar.getInstance().getTime();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String reqMeetingTime = "14:11";
        Time meetingHour = new Time(timeFormat.parse(reqMeetingTime).getTime());

        String meetingDateFormat = timeFormat.format(meetingHour.getTime());
        String todaysDateFormat = timeFormat.format(todaysDate);

        Date parse = timeFormat.parse(todaysDateFormat);
        Date parse1 = timeFormat.parse(meetingDateFormat);

        if(parse1.after(parse)){

            System.out.println("pippo");

        }
    }
}
