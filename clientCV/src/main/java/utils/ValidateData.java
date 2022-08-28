// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package utils;

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
     * sono ammessi spazi, lunghezza minima 8 caratteri
     */
    private static final String PASSWORD_STRUCTURE = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!?@#$%^&+=])(?=\\S+$).{8,}$";

    /**
     * Struttura della stringa da associare all'email, sottoforma di espressione
     * regolare (regex). formato: @ obbligatoria, ammessi tutti i caratteri
     * maiuscoli / minuscoli, numeri e i caratteri (.), (-)
     */
    private static final String EMAIL_STRUCTURE = "^[a-zA-Z0-9.-]+@[a-zA-Z0-9.-]+$";

    /**
     * Struttura della stringa da associare al codice fiscale, sottoforma di espressione
     * regolare (regex).
     */
    private static final String CODF_STRUCTURE = "^[A-Z]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}+$";

    /**
     * Pattern compilato della password
     */
    private static final Pattern passwordPattern = Pattern.compile(PASSWORD_STRUCTURE);

    /**
     * Pattern compilato dell'email
     */
    private static final Pattern emailPattern = Pattern.compile(EMAIL_STRUCTURE);

    /**
     * Pattern compilato del codice fiscale
     */
    private static final Pattern codfPattern = Pattern.compile(CODF_STRUCTURE);

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

    /**
     * Controlla se il codice fiscale in input rispetta il pattern richiesto
     *
     * @param codf il codice fiscale da controllare
     * @return true se il controllo va a buon fine, false altrimenti
     */
    public static boolean validateCodf(String codf) {
        Matcher matcher = codfPattern.matcher(codf);
        return matcher.matches();
    }
}
