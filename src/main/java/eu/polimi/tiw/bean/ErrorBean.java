package eu.polimi.tiw.bean;

public class ErrorBean {

    public ErrorBean(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorBean() {
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
