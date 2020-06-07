package eu.polimi.tiw.bean;

public class EmployeeBean {


    public enum Parameters {

        name("^([ \\u00c0-\\u01ffa-zA-Z'’\\-\\p{L}]){0,255}+$"),
        surname("^([ \\u00c0-\\u01ffa-zA-Z'’\\-\\p{L}]){0,255}+$"),

        email("^(?=.{1,255}$)[_A-Za-z0-9-\\+\\p{L}]+(\\.[_A-Za-z0-9-\\p{L}]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"),

        password("^(?=.*[A-HJ-K-NP-Z])(?=.*\\d).+$");

        private String regEx;

        Parameters(String regEx) {
            this.regEx = regEx;
        }

        public String regEx() {
            return regEx;
        }

    }

    private int employeeId;
    private String name;
    private String surname;
    private String email;
    private String passKey;

    public EmployeeBean() {
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
