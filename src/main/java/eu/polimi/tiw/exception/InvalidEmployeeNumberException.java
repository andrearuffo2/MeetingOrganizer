package eu.polimi.tiw.exception;

public class InvalidEmployeeNumberException extends AppCrash{

    public InvalidEmployeeNumberException(String errorMessage) {
        super(errorMessage);
    }
}
