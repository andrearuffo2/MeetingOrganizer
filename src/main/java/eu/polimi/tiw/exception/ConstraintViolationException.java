package eu.polimi.tiw.exception;

public class ConstraintViolationException extends AppCrash{

    public ConstraintViolationException(String errorMessage) {
        super(errorMessage);
    }
}
