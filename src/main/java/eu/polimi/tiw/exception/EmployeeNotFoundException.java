package eu.polimi.tiw.exception;

public class EmployeeNotFoundException extends AppCrash{

    public EmployeeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
