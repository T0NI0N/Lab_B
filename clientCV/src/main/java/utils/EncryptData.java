package utils;

import java.security.*;

/**
 * Classe adibita alla criptazione dei dati tramite MD5
 */

public class EncryptData {
    /**
     * Utilizza l'agoritmo MD5 per criptare una stringa passata in input
     * 
     * @param input stringa da criptare
     * @return una stringa criptata tramite l'algoritmo MD5
     */
    public static String encrypt(String input) {
        String output = "";
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] passBytes = input.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for (byte elemento : digested) {
                sb.append(Integer.toHexString(0xff & elemento));
            }
            output = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return output;
    }
}
