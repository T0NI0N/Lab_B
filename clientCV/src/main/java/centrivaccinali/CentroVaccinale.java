// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import java.io.Serializable;
import enums.TipoCentroVaccinale;

/**
 * Classe che rappresenta un oggetto di tipo CentroVaccinale
 */
public class CentroVaccinale implements Serializable {

    private static final long serialVersionUID = 1L;

    // campi

    /**
     * Rappresenta il nome del centro vaccinale
     */
    private String nomeCentro;

    /**
     * Rappresenta l'indirizzo del centro vaccinale {@link Indirizzo}
     */
    private Indirizzo indirizzo;

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
     */
    public CentroVaccinale(String n, Indirizzo i, TipoCentroVaccinale t) {
        nomeCentro = n;
        indirizzo = i;
        tipo = t;
    }

    // metodi

    /**
     * Ritorna l'indirizzo del centro vaccinale
     *
     * @return la stringa contenente l'indirizzo del centro vaccinale
     */
    public String getIndirizzo() {
        return indirizzo.toString();
    }

    /**
     * Imposta l'indirizzo del centro vaccinale
     *
     * @param i l'indirizzo del centro vaccinale
     */
    public void setIndirizzo(Indirizzo i) {
        indirizzo = i;
    }

    /**
     * Ritorna il qualificatore dell'indirizzo del centro vaccinale
     *
     * @return la stringa contenente il qualificatore dell'indirizzo del centro
     *         vaccinale
     */
    public String getQualificatore() {
        return indirizzo.getQualificatore();
    }

    /**
     * Ritorna il nome dell'indirizzo del centro vaccinale
     *
     * @return il nome dell'indirizzo del centro vaccinale
     */
    public String getNome() {
        return indirizzo.getNome();
    }

    /**
     * Ritorna il numero civico dell'indirizzo del centro vaccinale
     *
     * @return il numero civico dell'indirizzo del centro vaccinale
     */
    public String getnCivico() {
        return indirizzo.getnCivico();
    }

    /**
     * Ritorna il comune del centro vaccinale
     *
     * @return il comune del centro vaccinale
     */
    public String getComune() {
        return indirizzo.getComune();
    }

    /**
     * Ritorna la provincia del centro vaccinale
     *
     * @return la provincia del centro vaccinale
     */
    public String getProvincia() {
        return indirizzo.getProvincia();
    }

    /**
     * Ritorna il cap dell'indirizzo del centro vaccinale
     *
     * @return il cap dell'indirizzo del centro vaccinale
     */
    public int getCap() {
        return indirizzo.getCap();
    }

    /**
     * Ritorna il tipo del centro vaccinale
     *
     * @return il tipo del centro vaccinale
     */
    public String getTipo() {
        return tipo.toString();
    }

    /**
     * Ritorna l'indice del tipo del centro vaccinale
     *
     * @return l'indice del tipo del centro vaccinale
     */
    public int getTipoInt() {
        return tipo.ordinal();
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
     * {@inheritDoc}
     *
     * Ritorna l'oggetto CentroVaccinale sotto forma di stringa, informazioni e
     * lista di eventi avversi
     */

    @Override
    public String toString() {
        return "Centro " + nomeCentro + ", " + indirizzo + ", " + tipo;
    }

}
