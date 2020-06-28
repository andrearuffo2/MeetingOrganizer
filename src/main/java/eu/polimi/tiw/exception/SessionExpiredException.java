package eu.polimi.tiw.exception;

public class SessionExpiredException extends AppCrash{

    public SessionExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
