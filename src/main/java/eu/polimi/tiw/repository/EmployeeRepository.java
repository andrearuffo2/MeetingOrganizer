package eu.polimi.tiw.repository;

public class EmployeeRepository {
    private int idDipendente;
    private String name;
    private String surname;
    private String email;
    private String passkey;

    public EmployeeRepository() {
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasskey() {
        return this.passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public int getIdDipendente() {
        return this.idDipendente;
    }

    public void setIdDipendente(int idDipendente) {
        this.idDipendente = idDipendente;
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