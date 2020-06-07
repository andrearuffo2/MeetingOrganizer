package eu.polimi.tiw.validation;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;
import org.apache.commons.lang3.*;

public class Validator {

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
            throw new AppCrash("invalid parameter employee name");
        if (!Utils.regExMatches(EmployeeBean.Parameters.email.regEx(), employeeBean.getEmail()))
            throw new AppCrash("invalid parameter employee name");
        if (!Utils.regExMatches(EmployeeBean.Parameters.password.regEx(), employeeBean.getPassKey()))
            throw new AppCrash("invalid parameter employee name");

    }

    public static void validateLogin(EmployeeBean employeeBean) throws AppCrash {
        if (StringUtils.isEmpty(employeeBean.getEmail()))
            throw new AppCrash("Employee email must not be null or empty");
        if (StringUtils.isEmpty(employeeBean.getPassKey()))
            throw new AppCrash("Employee passkey must not be null or empty");
    }
}
