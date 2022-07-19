// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import java.io.Serializable;
import enums.TipoEventoAvverso;

/**
 * Classe che rappresenta un oggetto di tipo EventiAvversi
 */
public class EventoAvverso implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Rappresenta l'evento avverso
     */
    private TipoEventoAvverso evento;
    /**
     * Rappresenta la severità dell'evento avverso
     */
    private double severita;
    /**
     * Rappresenta le note opzionali associate all'evento avverso
     */
    private String note;

    /**
     * Costruttore dell'oggetto EventiAvversi.
     * 
     * @param e Sintomo della segnalazione.
     * @param s Severità del sintomo.
     * @param n Nota aggiuntiva della segnalazione (255 caratteri max).
     */
    public EventoAvverso(TipoEventoAvverso e, double s, String n) {
        evento = e;
        severita = s;
        note = n;
    }

    /**
     * Restituisce il sintomo, o evento avverso, della segnalazione
     * 
     * @return Il valore di ritorno è un'istanza della variabile enum Evento
     */
    public TipoEventoAvverso getEvento() {
        return evento;
    }

    /**
     * Imposta su evento il valore passato con il parametro
     * 
     * @param evento valore da salvare all'interno di evento
     */
    public void setEvento(TipoEventoAvverso evento) {
        this.evento = evento;
    }

    /**
     * Restituisce la severità del sintomo
     * 
     * @return Il valore di ritorno è un intero
     */
    public double getSeverita() {
        return severita;
    }

    /**
     * Imposta su severita il valore del parametro passato in input
     * 
     * @param severita valore da salvare su severita
     */
    public void setSeverita(double severita) {
        this.severita = severita;
    }

    /**
     * Restituisce la nota aggiuntiva della segnalazione
     * 
     * @return Il valore restituito è una stringa di lunghezza massima 255 caratteri
     */
    public String getNote() {
        String output = "Nessuna nota";
        if (!note.isEmpty()) {
            output = note;
        }
        return output;
    }

    /**
     * Imposta il valore di note sul parametro passato in input
     * 
     * @param note stringa da salvare su note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Ritorna l'oggetto EventiAvversi sotto forma di stringa
     * 
     * @return una stringa che contiene tutti i valori dei campi di EventiAvversi
     */
    public String toString() {
        return evento + " | severità media: " + severita + " | " + note;
    }
}
