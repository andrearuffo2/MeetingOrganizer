package eu.polimi.tiw.exception;

public class BadPasswordException extends AppCrash{

    public BadPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
