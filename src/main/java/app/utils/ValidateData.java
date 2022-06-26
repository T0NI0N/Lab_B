package app.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * classe di supporto usata per il controllo degli input dell'utente tramite
 * espressioni regolari
 */

public class ValidateData {

    /**
     * Struttura della stringa da associare alla password, sottoforma di espressione
     * regolare (regex). condizioni: almeno un numero, almeno un carattere
     * maiuscolo, almeno un carattere minuscolo, almeno un carattere speciale, non
     * sono ammessi spazi, lunghezza compresa tra 8 e 20 caratteri
     */
    private static final String PASSWORD_STRUCTURE = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    /**
     * Struttura della stringa da associare all'email, sottoforma di espressione
     * regolare (regex). formato: @ obbligatoria, ammessi tutti i caratteri
     * maiuscoli / minuscoli, numeri e i caratteri (.), (-)
     */
    private static final String EMAIL_STRUCTURE = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";

    /**
     * Pattern compilato della password
     */
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_STRUCTURE);

    /**
     * Pattern compilato dell'email
     */
    private static final Pattern emailPattern = Pattern.compile(EMAIL_STRUCTURE);

    /**
     * Controlla se la password in input rispetta il pattern richiesto
     * 
     * @param psswd la password da controllare
     * @return true se il controllo va a buon fine, false altrimenti
     */
    public static boolean validatePassword(String psswd) {
        Matcher matcher = passwordPattern.matcher(psswd);
        return matcher.matches();
    }

    /**
     * Controlla se l'email in input rispetta il pattern richiesto
     * 
     * @param email l'email da controllare
     * @return true se il controllo va a buon fine, false altrimenti
     */
    public static boolean validateMail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
