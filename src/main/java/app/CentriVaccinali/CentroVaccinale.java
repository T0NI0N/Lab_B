package app.CentriVaccinali;

import app.TipoCentroVaccinale;

/**
 * Classe che rappresenta un oggetto di tipo CentroVaccinale
 */
public class CentroVaccinale {
    // campi

    /**
     * Rappresenta il nome del centro vaccinale
     */
    private String nomeCentro;

    /**
     * Rappresenta il tipo di centro vaccinale
     */
    private TipoCentroVaccinale tipo;

    // costruttori

    /**
     * Ritorna un oggetto di tipo CentroVaccinale
     * 
     * @param n il nome del centro
     * @param i l'indirizzo del centro
     * @param t il tipo del centro
     * @param e la lista di eventi avversi registrati
     */
    public CentroVaccinale(String n, TipoCentroVaccinale t) {
        nomeCentro = n;

        tipo = t;

    }

    // metodi

    /**
     * Ritorna il tipo del centro vaccinale
     * 
     * @return il tipo del centro vaccinale
     */
    public String getTipo() {
        return tipo.toString();
    }

    /**
     * Imposta il tipo di centro vaccinale
     * 
     * @param t il tipo del centro vaccinale
     */
    public void setTipo(TipoCentroVaccinale t) {
        tipo = t;
    }

    /**
     * Ritorna il nome del centro vaccinale
     * 
     * @return il nome del centro vaccinale
     */
    public String getNomeCentro() {
        return nomeCentro;
    }

    /**
     * Ritorna l'oggetto CentroVaccinale sotto forma di stringa, informazioni e
     * lista di eventi avversi
     * 
     * @return una stringa che contiene tutti i valori dei campi di CentroVaccinale
     */
    public String toString() {
        String output = nomeCentro + ", " + tipo + ", eventi avversi:";

        return output;
    }

}