package eu.polimi.tiw.validation;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.request.*;
import org.apache.commons.lang3.*;

import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.Date;

public class Validator {

    private static int badInvitedEmployeeCounter = 0;

    public static void validateRegistration(EmployeeBean employeeBean) throws AppCrash {

        if (StringUtils.isEmpty(employeeBean.getName()))
            throw new AppCrash("Employee name must not be null or empty");
        if (StringUtils.isEmpty(employeeBean.getSurname()))
            throw new AppCrash("Employee surname must not be null or empty");
        if (StringUtils.isEmpty(employeeBean.getEmail()))
            throw new AppCrash("Employee email must not be null or empty");
        if (StringUtils.isEmpty(employeeBean.getPassKey()))
            throw new AppCrash("Employee passkey must not be null or empty");

        // Data validity check
        if (!Utils.regExMatches(EmployeeBean.Parameters.name.regEx(), employeeBean.getName()))
            throw new AppCrash("invalid parameter employee name");
        if (!Utils.regExMatches(EmployeeBean.Parameters.surname.regEx(), employeeBean.getSurname()))
            throw new AppCrash("invalid parameter employee surname");
        if (!Utils.regExMatches(EmployeeBean.Parameters.email.regEx(), employeeBean.getEmail()))
            throw new AppCrash("invalid parameter employee email");
        if (!Utils.regExMatches(EmployeeBean.Parameters.password.regEx(), employeeBean.getPassKey()))
            throw new AppCrash("invalid parameter employee password");

    }

    public static void validateLogin(EmployeeBean employeeBean) throws AppCrash {
        if (StringUtils.isEmpty(employeeBean.getEmail()))
            throw new AppCrash("Employee email must not be null or empty");
        if (StringUtils.isEmpty(employeeBean.getPassKey()))
            throw new AppCrash("Employee passkey must not be null or empty");
    }

    public static void validateMeetingInsert(MeetingBean meetingBean) throws AppCrash, ParseException {

        Calendar meetingData = Calendar.getInstance();
        meetingData.setTime(meetingBean.getMeetingData());
        Date todaysDate = Calendar.getInstance().getTime();

        Time meetingHour = meetingBean.getMeetingHour();

        LocalDate todaysDateToCompare = LocalDate.now();
        LocalDate meetingDataToCompare = LocalDate.of(meetingData.get(Calendar.YEAR), meetingData.get(Calendar.MONTH) +1, meetingData.get(Calendar.DAY_OF_MONTH));


        if(meetingDataToCompare.compareTo(todaysDateToCompare) < 0){
            throw new AppCrash("The meeting date must be after the current date");
        }

        if(todaysDateToCompare.compareTo(meetingDataToCompare) == 0){

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String meetingTimeFormat = timeFormat.format(meetingHour.getTime());
            String todaysTimeFormat = timeFormat.format(todaysDate);

            Date meetingTime = timeFormat.parse(meetingTimeFormat);
            Date todayTime = timeFormat.parse(todaysTimeFormat);

            if(meetingTime.before(todayTime)){
                throw new AppCrash("The meeting time can only be after the current one");
            }

        }

        if(meetingBean.getInvolvedEmployeeNumber() == 0){
            throw new AppCrash("The meeting must be composed by at least 1 memeber other than you");
        }

        if(meetingBean.getMeetingsDuration() == 0){
            throw new AppCrash("The meeting duration must be at least 1 hour");
        }

    }



    public static void validateSaveMeetingRequest(SaveMeetingRequest request, int involvedEmployeeNumber) throws InvalidEmployeeNumberException, MaximumNumberOfTryException {

        int invitedEmployeeListSize = request.getInvitedEmployeeList().size();

        //Validation of selected partecipants
        if(invitedEmployeeListSize > involvedEmployeeNumber){
            int numberOfEmployeeToRemoveFromInvitedList = invitedEmployeeListSize - involvedEmployeeNumber;
            badInvitedEmployeeCounter++;
            checkNumberOfWrongRequest(badInvitedEmployeeCounter);
            throw new InvalidEmployeeNumberException("Too many employees selected. Please remove at least " + numberOfEmployeeToRemoveFromInvitedList +" of them");
        }

    }

    private static void checkNumberOfWrongRequest(int badInvitedEmployeeCounter) throws MaximumNumberOfTryException {
        if(badInvitedEmployeeCounter > 2){
            badInvitedEmployeeCounter = 0;
            throw new MaximumNumberOfTryException("The maximum limit of incorrect requests has been reached. Please go back to the home page and try to insert a new request");
        }

    }

}
