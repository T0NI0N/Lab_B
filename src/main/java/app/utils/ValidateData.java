package app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateData {
    
    private static final String PASSWORD_STRUCTURE = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    private static final String EMAIL_STRUCTURE = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";

    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_STRUCTURE);

    private static final Pattern emailPattern = Pattern.compile(EMAIL_STRUCTURE);

    public static boolean validatePassword(String psswd) {
        Matcher matcher = passwordPattern.matcher(psswd);
        return matcher.matches();
    }

    public static boolean validateMail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
