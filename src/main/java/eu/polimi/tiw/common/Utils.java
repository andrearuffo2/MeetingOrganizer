package eu.polimi.tiw.common;

import javax.servlet.http.*;
import java.io.*;
import java.util.regex.*;

public class Utils {

    public static boolean regExMatches(String regEx, String string){

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(string);

        return matcher.matches();

    }

}
