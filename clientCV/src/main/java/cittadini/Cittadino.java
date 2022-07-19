// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package cittadini;

import enums.TipoVaccino;
import java.io.Serializable;


/**
 * Classe che rappresenta un oggetto di tipo Cittadino
 */
public class Cittadino implements Serializable {

    private static final long serialVersionUID = 1L;

    // campi

    /**
     * Rappresenta il nome del cittadino
     */
    private String nome;
    /**
     * Rappresenta il cognome del cittadino
     */
    private String cognome;
    /**
     * Rappresenta il codice fiscale del cittadino
     */
    private String codiceFiscale;
    /**
     * Rappresenta l'email di registrazione del cittadino
     */
    private String email;
    /**
     * Rappresenta il nome utente di registrazione del cittadino
     */
    private String userid;
    /**
     * Rappresenta la password di registrazione del cittadino
     */
    private String password;
    /**
     * Rappresenta l'id univoco di vaccinazione del cittadino
     */
    private long idVaccinazione;
    /**
     * Rappresenta la data di vaccinazione del cittadino
     */
    private String dataSomministrazione;
    /**
     * Rappresenta il tipo di vaccino somministrato
     */
    private TipoVaccino tipo;

    // costruttori

    /**
     * Ritorna un oggetto di tipo Cittadino
     * 
     * @param nome    del cittadino
     * @param cognome del cittadino
     * @param cf      codice fiscale del cittadino
     * @param mail    di registrazione del cittadino
     * @param uid     nome utente di registrazione del cittadino
     * @param psswd   password di registrazione del cittadino
     * @param id      univoco vaccinazione
     */
    public Cittadino(String nome, String cognome, String cf, String mail, String uid, String psswd, long id) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = cf;
        this.email = mail;
        this.userid = uid;
        this.password = psswd;
        this.idVaccinazione = id;
    }

    /**
     * Ritorna un oggetto di tipo Cittadino
     * 
     * @param nome    del cittadino
     * @param cognome del cittadino
     * @param cf      codice fiscale del cittadino
     * @param data    di somministrazione del vaccino
     * @param tipo    di vaccino somministarato
     * @param id      univoco vaccinazione
     */
    public Cittadino(String nome, String cognome, String cf, String data, TipoVaccino tipo, long id) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = cf;
        this.dataSomministrazione = data;
        this.tipo = tipo;
        this.idVaccinazione = id;
    }

    /**
     * Ritorna un oggetto di tipo Cittadino
     * 
     * @param nome           del cittadino
     * @param cognome        del cittadino
     * @param codicefiscale  del cittadino
     * @param email          di registrazione del cittadino
     * @param userid         di registrazione del cittadino
     * @param password       di registrazione del cittadino
     * @param idvaccinazione univoco vaccinazione
     * @param data           di somministrazione del vaccino
     * @param tipo           di vaccino somministarato
     */
    public Cittadino(String nome, String cognome, String codicefiscale, String email, String userid, String password,
            long idvaccinazione, String data, TipoVaccino tipo) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codicefiscale;
        this.email = email;
        this.userid = userid;
        this.password = password;// TODO inserire una criptazione per la password nel costruttore
        this.idVaccinazione = idvaccinazione;
        this.dataSomministrazione = data;
        this.tipo = tipo;
    }

    /**
     * Ritorna l'oggetto Cittadino sottoforma di stringa
     * 
     * @return una stringa che contiene tutti i valori dei campi di Cittadino
     */
    public String toString() {
        return "cittadino [id= " + this.idVaccinazione + ", cognome=" + this.cognome + ", nome=" + this.nome
                + ", codice fiscale=" + this.codiceFiscale + ", email=" + this.email + ", userid=" + this.userid
                + ", password=" + this.password + "]";
    }

    /**
     * Ritorna l'id univoco di vaccinazione
     * 
     * @return l'id univoco di vaccinazione
     */
    public long getIdVaccinazione() {
        return idVaccinazione;
    }

    /**
     * Imposta l'id univoco di vaccinazione
     * 
     * @param idVaccinazione univoco
     */
    public void setIdVaccinazione(long idVaccinazione) {
        this.idVaccinazione = idVaccinazione;
    }

    /**
     * Ritorna la password di registrazione del cittadino
     * 
     * @return password di registrazione
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password di registrazione del cittadino
     * 
     * @param password da impostare
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Ritorna il nome utente di registrazione del cittadino
     * 
     * @return il nome utente di registrazione
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Imposta il nome utente di registrazione del cittadino
     * 
     * @param userid di registrazione del cittadino
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Ritorna l'email di registrazione del cittadino
     * 
     * @return l'email di registrazione
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email di registrazione del cittadino
     * 
     * @param email di registrazione del cittadino
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Ritorna il codice fiscale del cittadino
     * 
     * @return codice fiscale del cittadino
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il codice fiscale del cittadino
     * 
     * @param codiceFiscale del cittadino
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Ritorna il cognome del cittadino
     * 
     * @return cognome del cittadino
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il cognome del cittadino
     * 
     * @param cognome del cittadino
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Ritorna il nome del cittadino
     * 
     * @return nome del cittadino
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome del cittadino
     * 
     * @param nome del cittadino
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Ritorna la data di somministrazione del vaccino
     * 
     * @return data somministrazione vaccino
     */
    public String getDataSomministrazione() {
        return dataSomministrazione;
    }

    /**
     * Imposta la data di somministarzione del vaccino
     * 
     * @param d la data di somministarzione del vaccino
     */
    public void setDataSomministrazione(String d) {
        dataSomministrazione = d;
    }

    /**
     * Ritorna il tipo di vaccino somministrato
     * 
     * @return tipo di vaccino somministrato
     */
    public TipoVaccino getTipo() {
        return tipo;
    }

    /**
     * Imposta il tipo di vaccino somministrato
     * 
     * @param t tipo di vaccino somministrato
     */
    public void setTipo(TipoVaccino t) {
        tipo = t;
    }

}
