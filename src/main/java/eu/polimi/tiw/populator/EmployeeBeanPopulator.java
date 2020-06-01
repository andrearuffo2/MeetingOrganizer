package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import org.apache.commons.lang3.*;

import javax.servlet.http.*;

public class EmployeeBeanPopulator {
    public EmployeeBeanPopulator() {
    }

    public static EmployeeBean populateRegister(HttpServletRequest req) throws AppCrash {

        String name = req.getParameter(MOConstants.NAME);
        String surname = req.getParameter(MOConstants.SURNAME);
        String email = req.getParameter(MOConstants.EMAIL);
        String password = req.getParameter(MOConstants.PASSWORD);

        EmployeeBean toPopulate = new EmployeeBean();
        toPopulate.setName(name);
        toPopulate.setSurname(surname);
        toPopulate.setEmail(email);
        toPopulate.setPassKey(password);

        return toPopulate;
    }

    public static EmployeeBean populateLogin(HttpServletRequest req) throws AppCrash {
        EmployeeBean toPopulate = new EmployeeBean();
        toPopulate.setEmail(req.getParameter("email"));
        toPopulate.setPassKey(req.getParameter("psw"));
        if (StringUtils.isNotEmpty(req.getParameter("refresh"))) {
            toPopulate.setEmployeeId(Integer.parseInt(req.getParameter("userId")));
            toPopulate.setRefreshPage(true);
        }

        return toPopulate;
    }
}
