package eu.polimi.tiw.common;

public class MOConstants {
    //DB COLUMNS
    public static String NAME_DB = "nome";
    public static String SURNAME_DB = "cognome";
    public static String PASSKEY_DB = "passkey";
    public static String EMPLOYEE_ID_DB = "id_dipendente";
    public static String EMPLOYEE_PASSKEY_DB = "passkey";
    public static String MEETING_ID_DB = "id_riunione";
    public static String MEETING_TITLE_DB = "titolo_riunione";
    public static String MEETING_DATE_DB = "data_riunione";
    public static String MEETING_HOUR_DB = "ora_riunione";
    public static String MEETING_DURATION_DB = "durata";
    public static String MEETING_INVOLVED_EMPLOYEE_NUMBER_DB = "numero_partecipanti";
    public static String MEETING_USERNAME_ORGANIZATOR_DB = "username_dipendente";
    public static final String CONSTRAINTS_VIOLATION = "You can't plan another meeting organized by you at same hour";

    //REQUEST REGISTER-LOGIN
    public static String NAME = "name";
    public static String SURNAME = "surname";
    public static String EMAIL = "email";
    public static String PASSWORD = "psw";
    public static String REFRESH = "refresh";

    //SESSION
    public static String SESSION_ATTRIBUTE  = "employee";
    public static String SESSION_EXPIRED_MESSAGE  = "Sessione expired. You will be redirected to the login page in a while!";


    public MOConstants() {
    }
}