package eu.polimi.tiw.bean;

public class EmployeeBean {
    private int employeeId;
    private String name;
    private String surname;
    private String email;
    private String passKey;
    private boolean refreshPage;

    public EmployeeBean() {
    }

    public boolean isRefreshPage() {
        return this.refreshPage;
    }

    public void setRefreshPage(boolean refreshPage) {
        this.refreshPage = refreshPage;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassKey() {
        return this.passKey;
    }

    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
