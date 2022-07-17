package centrivaccinali;

import java.io.Serializable;

/**
 * Classe che rappresenta un oggetto di tipo indirizzo, di supporto a
 * {@link CentroVaccinale}
 */
public class Indirizzo implements Serializable {

    private static final long serialVersionUID = 1L;

    // campi

    /**
     * Rappresenta il qualificatore es. via, viale, piazza
     */
    private String qualificatore;

    /**
     * Rappresenta il nome dell'indirizzo del centro vaccinale
     */
    private String nome;

    /**
     * Rappresenta il numero civico dell'indirizzo del centro vaccinale
     */
    private String nCivico;

    /**
     * Rappresenta il comune di appartenenza del centro vaccinale
     */
    private String comune;

    /**
     * Rappresenta la provincia di appartenenza del centro vaccinale
     */
    private String provincia;

    /**
     * Rappresenta il cap (codice di avviamento postale) del comune del centro
     * vaccinale
     */
    private int cap;

    // costruttore
    /**
     * Ritorna un oggetto di tipo Indirizzo
     * 
     * @param q   il qualificatore dell'indirizzo
     * @param n   il nome dell'indirizzo
     * @param nC  il numero civico dell'indirizzo
     * @param c   il comune del centro vaccinale
     * @param p   la provincia del centro vaccinale
     * @param cap il cap del comune del centro vaccinale
     */
    public Indirizzo(String q, String n, String nC, String c, String p, int cap) {
        qualificatore = q;
        nome = n;
        nCivico = nC;
        comune = c;
        provincia = p;
        this.cap = cap;
    }

    // metodi

    /**
     * Ritorna il qualificatore
     * 
     * @return il qualificatore dell'indirizzo
     */
    public String getQualificatore() {
        return qualificatore;
    }

    /**
     * Ritorna il nome dell'indirizzo
     * 
     * @return il nome dell'indirizzo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna il numero civico
     * 
     * @return il numero civico dell'indirizzo
     */
    public String getnCivico() {
        return nCivico;
    }

    /**
     * Ritorna il comune del centro vaccinale
     * 
     * @return il comune del centro vaccinale
     */
    public String getComune() {
        return comune;
    }

    /**
     * Ritorna la provincia del centro vaccinale
     * 
     * @return la provincia del centro vaccinale
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Ritorna il cap del centro vaccinale
     * 
     * @return il cap del centro vaccinale
     */
    public int getCap() {
        return cap;
    }

    /**
     * Ritorna l'oggetto indirizzo sotto forma di stringa
     * 
     * @return una stringa che contiene tutti i valori dei campi di Indirizzo
     */
    public String toString() {

        return qualificatore + " " + nome + ", " + nCivico + ", " + comune + ", " + provincia + ", " + cap;
    }
}
