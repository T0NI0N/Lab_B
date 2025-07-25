package centrivaccinali;// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

import enums.TipoCentroVaccinale;
import enums.TipoEventoAvverso;
import enums.TipoVaccino;
import utils.EncryptData;

import java.sql.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Classe che gestisce la base di dati del server
 */
public class DatabaseHandler implements ConnectionHandlerInterface {
    private String connection = "jdbc:postgresql://";
    private Connection conn;
    private int idVaccinazione;

    /*
     * Costruttore di DatabaseHandler, tenta di connettersi all'host utilizzando utente e password passati come parametro. In caso dovesse riuscire a connettersi e non ci dovessero essere le tabelle TipiCentri, Comuni, Indirizzi, CentriVaccinali, Cittadini_Registrati, TipiVaccini, TipiEventi, EventiAvversi, assegnando a TipiCentri, TipiVaccini e TipiEventi i valori salvati nelle classi TipoCentroVaccinale, TipoVaccino e TipoEventoAvverso
     *
     * @param host Host a cui connettersi
     * @param user L'utente utilizzato per connettersi all'host
     * @param password La password dell'utente utilizzato per connettersi all'host
     */
    public DatabaseHandler(String host, String user, String password) {
        connection += host + "/";
        try {
            try {
                conn = DriverManager.getConnection(connection + "db_vaccini", user, password);
            } catch (Exception ex) {
                conn = DriverManager.getConnection(connection, user, password);
                Statement statement = conn.createStatement();
                statement.executeUpdate("CREATE DATABASE db_vaccini");
                conn = DriverManager.getConnection(connection + "db_vaccini", user, password);
                statement = conn.createStatement();
                int id = 0;
                statement.executeUpdate(
                        "CREATE TABLE TipiCentri (" +
                                "idTipologia smallint PRIMARY KEY, " +
                                "valore varchar(20) NOT NULL)");
                for (TipoCentroVaccinale tipo : TipoCentroVaccinale.values()) {
                    statement.executeUpdate(
                            "INSERT INTO TipiCentri (idTipologia, valore) VALUES (" +
                                    id + ", '" +
                                    tipo + "')");
                    id++;
                }
                statement.executeUpdate(
                        "CREATE TABLE Comuni (" +
                                "idComune smallint PRIMARY KEY, " +
                                "nome varchar(35) NOT NULL, " +
                                "cap varchar(5) NOT NULL, " +
                                "provincia varchar(4) NOT NULL)");
                statement.executeUpdate(
                        "CREATE TABLE Indirizzi (" +
                                "idIndirizzo integer PRIMARY KEY, " +
                                "qualificatore varchar(10) NOT NULL, " +
                                "nome varchar(40) NOT NULL, " +
                                "n_civico varchar(5) NOT NULL, " +
                                "idComune smallint, " +
                                "CONSTRAINT fk_Comuni FOREIGN KEY (idComune) REFERENCES Comuni(idComune))");
                statement.executeUpdate(
                        "CREATE TABLE CentriVaccinali (" +
                                "idCentroVaccinale smallint PRIMARY KEY, " +
                                "nome varchar(30) NOT NULL, " +
                                "idTipologia smallint, " +
                                "idIndirizzo integer, " +
                                "CONSTRAINT fk_TipiCentri FOREIGN KEY (idTipologia) REFERENCES TipiCentri(idTipologia), "
                                +
                                "CONSTRAINT fk_Indirizzi FOREIGN KEY (idIndirizzo) REFERENCES Indirizzi(idIndirizzo))");
                statement.executeUpdate(
                        "CREATE TABLE Cittadini_Registrati (" +
                                "idCittadino integer PRIMARY KEY, " +
                                "nome varchar(20) NOT NULL, " +
                                "cognome varchar(20) NOT NULL, " +
                                "email varchar(50) NOT NULL DEFAULT '', " +
                                "userid varchar(20) NOT NULL DEFAULT '', " +
                                "password varchar(100) NOT NULL DEFAULT '', " +
                                "codicefiscale varchar(16) NOT NULL, " +
                                "idVaccinazione integer DEFAULT 0, " +
                                "idCentroVaccinale smallint," +
                                "CONSTRAINT fk_CentriVaccinali FOREIGN KEY (idCentroVaccinale) REFERENCES CentriVaccinali(idCentroVaccinale))");
                id = 0;
                statement.executeUpdate(
                        "CREATE TABLE TipiVaccini (" +
                                "idTipologia smallint PRIMARY KEY, " +
                                "valore varchar(20) NOT NULL)");
                for (TipoVaccino tipo : TipoVaccino.values()) {
                    statement.executeUpdate(
                            "INSERT INTO TipiVaccini (idTipologia, valore) VALUES (" +
                                    id + ", '" +
                                    tipo + "')");
                    id++;
                }
                id = 0;
                statement.executeUpdate(
                        "CREATE TABLE TipiEventi (" +
                                "idTipologia smallint PRIMARY KEY, " +
                                "valore varchar(20) NOT NULL)");
                for (TipoEventoAvverso tipo : TipoEventoAvverso.values()) {
                    statement.executeUpdate(
                            "INSERT INTO TipiEventi (idTipologia, valore) VALUES (" +
                                    id + ", '" +
                                    tipo + "')");
                    id++;
                }
                statement.executeUpdate(
                        "CREATE TABLE EventiAvversi (" +
                                "idEvento integer PRIMARY KEY, " +
                                "severità integer NOT NULL, " +
                                "note varchar(255) NOT NULL, " +
                                "idCentroVaccinale smallint," +
                                "idCittadino integer, " +
                                "idTipologia smallint, " +
                                "CONSTRAINT fk_CentriVaccinali FOREIGN KEY (idCentroVaccinale) REFERENCES CentriVaccinali(idCentroVaccinale), "
                                +
                                "CONSTRAINT fk_Cittadini FOREIGN KEY (idCittadino) REFERENCES Cittadini_Registrati(idCittadino), "
                                +
                                "CONSTRAINT fk_TipiEventi FOREIGN KEY (idTipologia) REFERENCES TipiEventi(idTipologia))");
                System.out.println("Creato il database...");
                fillDataBase();
            }
            System.out.println("Connesso al database...");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        getLastId();
    }

    /**
     * Salva in una nuova riga di Cittadini_Registrati i valori del cittadino passato come parametro, assegnando come idCentroVaccinale quello del centro con il nome passato come parametro
     *
     * @param user       Il cittadino da registrare
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public synchronized String registerCitizen(Cittadino user, String centername) {
        String output = "ok";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = conn
                    .prepareStatement("SELECT idCittadino FROM Cittadini_Registrati ORDER BY idCittadino DESC")
                    .executeQuery();
            int id;
            try {
                rs.next();
                id = rs.getInt("idCittadino");
            } catch (Exception ex1) {
                id = 0;
                output = ex1.toString();
            }
            id++;
            int centrovaccinale;
            try {
                rs = conn
                        .prepareStatement("SELECT idCentroVaccinale FROM CentriVaccinali WHERE nome LIKE '%" + centername + "%' ORDER BY idCentroVaccinale DESC")
                        .executeQuery();
                rs.next();
                centrovaccinale = rs.getInt("idCentroVaccinale");
            } catch (Exception ex1) {
                centrovaccinale = 0;
                output = ex1.toString();
            }
            statement.executeUpdate(
                    "INSERT INTO Cittadini_Registrati (idCittadino, nome, cognome, email, userid, password, codicefiscale, idCentroVaccinale) VALUES ("
                            +
                            id + ", '" +
                            user.getNome() + "', '" +
                            user.getCognome() + "', '" +
                            user.getEmail() + "', '" +
                            user.getUserid() + "', '" +
                            user.getPassword() + "', '" +
                            user.getCodiceFiscale() + "', " +
                            centrovaccinale + ")");
            System.out.println("Inserito cittadino");
            output = "ok";
        } catch (Exception e) {
            System.out.println(e);
            output = e.toString();
        }
        return output;
    }

    /**
     * Salva in una nuova riga di CentriVaccinali i valori del centro passato come parametro
     *
     * @param center Il centro da registrare
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public synchronized String registerCenter(CentroVaccinale center) {
        String output = "ok";
        try {
            boolean check = false;
            Statement statement = conn.createStatement();
            try {
                ResultSet rs = conn.prepareStatement(
                                "SELECT idCentroVaccinale FROM CentriVaccinali WHERE nome='" + center.getNomeCentro() + "'")
                        .executeQuery();
                rs.next();
                rs.getInt("idCentroVaccinale");
                System.out.println("Centro vaccinale presente nel database");
                output = "already_in";
            } catch (Exception ex) {
                check = true;
                output = ex.toString();
            }
            if (check) {
                try {
                    ResultSet rs = conn.prepareStatement(
                                    "SELECT idCentroVaccinale FROM CentriVaccinali cv JOIN (Indirizzi i JOIN Comuni com ON i.idComune=com.idComune) ON cv.idIndirizzo=i.idIndirizzo WHERE cv.nome='"
                                            + center.getNomeCentro() + "' AND  com.nome='" + center.getComune()
                                            + "' AND com.cap='" + center.getCap() + "' AND i.nome='" + center.getNome()
                                            + "' AND i.qualificatore='" + center.getQualificatore() + "' AND i.n_civico='"
                                            + center.getnCivico() + "'")
                            .executeQuery();
                    rs.next();
                    rs.getInt("idCentroVaccinale");
                    System.out.println("Centro vaccinale presente nel database");
                } catch (Exception ex) {
                    output = ex.toString();
                    ResultSet rs = conn
                            .prepareStatement(
                                    "SELECT idCentroVaccinale FROM CentriVaccinali ORDER BY idCentroVaccinale DESC")
                            .executeQuery();
                    int id;
                    try {
                        rs.next();
                        id = rs.getInt("idCentroVaccinale");
                    } catch (Exception ex1) {
                        id = 0;
                        output = ex1.toString();
                    }
                    id++;
                    int indirizzo;
                    try {
                        rs = conn.prepareStatement(
                                        "SELECT idIndirizzo FROM Indirizzi i JOIN Comuni com ON i.idComune=com.idComune WHERE i.nome='"
                                                + center.getNome() + "' AND i.qualificatore='" + center.getQualificatore()
                                                + "' AND i.n_civico='" + center.getnCivico() + "' AND com.nome='"
                                                + center.getComune() + "' AND com.cap='" + center.getCap() + "'")
                                .executeQuery();
                        rs.next();
                        indirizzo = rs.getInt("idIndirizzo");
                    } catch (Exception ex1) {
                        output = ex1.toString();
                        try {
                            rs = conn.prepareStatement("SELECT idIndirizzo FROM Indirizzi ORDER BY idIndirizzo DESC")
                                    .executeQuery();
                            rs.next();
                            indirizzo = rs.getInt("idIndirizzo");
                        } catch (Exception ex2) {
                            indirizzo = 0;
                            output = ex2.toString();
                        }
                        indirizzo++;
                        int comune;
                        try {
                            rs = conn.prepareStatement("SELECT idComune FROM Comuni WHERE nome='" + center.getComune()
                                    + "' AND cap='" + center.getCap() + "'").executeQuery();
                            rs.next();
                            comune = rs.getInt("idComune");
                            System.out.println(comune);
                        } catch (Exception ex2) {
                            output = ex2.toString();
                            try {
                                rs = conn.prepareStatement("SELECT idComune FROM Comuni ORDER BY idComune DESC")
                                        .executeQuery();
                                rs.next();
                                comune = rs.getInt("idComune");
                            } catch (Exception ex3) {
                                comune = 0;
                                output = ex3.toString();
                            }
                            comune++;
                            statement.executeUpdate(
                                    "INSERT INTO Comuni (idComune, nome, cap, provincia) VALUES (" +
                                            comune + ", '" +
                                            center.getComune() + "', '" +
                                            center.getCap() + "', '" +
                                            center.getProvincia() + "')");
                        }
                        statement.executeUpdate(
                                "INSERT INTO Indirizzi (idIndirizzo, qualificatore, nome, n_civico, idComune) VALUES ("
                                        +
                                        indirizzo + ", '" +
                                        center.getQualificatore() + "', '" +
                                        center.getNome() + "', '" +
                                        center.getnCivico() + "', " +
                                        comune + ")");
                    }
                    statement.executeUpdate(
                            "INSERT INTO CentriVaccinali (idCentroVaccinale, nome, idTipologia, idIndirizzo) VALUES (" +
                                    id + ", '" +
                                    center.getNomeCentro() + "', " +
                                    center.getTipoInt() + ", " +
                                    indirizzo + ")");
                    System.out.println("Inserito centro");
                    output = "ok";
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            output = e.toString();
        }
        return output;
    }

    /**
     * Salva un nuovo cittadino con solo nome, cognome, codice fiscale e id del centro vaccinale per poi assegnargli i dati della vaccinazione registrando in Vaccinati_(nomecentro) i dati e le associazioni tra cittadino e centro vaccinale
     *
     * @param user       I dati del cittadino e della vaccinazione da registrare
     * @param centername Il nome del centro in cui viene registrata la vaccinazione
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public synchronized String registerVaccination(Cittadino user, String centername) {
        String output = "ok";
        try {
            if(!checkCodiceFiscale(user.getCodiceFiscale()))
            {
                registerCitizen(user, centername);
                int centrovaccinale;
                int cittadino;
                ResultSet rs = conn.prepareStatement(
                                "SELECT c.idCittadino AS cidCittadino, c.idCentroVaccinale AS cidCentroVaccinale, cv.nome AS cvnome FROM Cittadini_Registrati c JOIN CentriVaccinali cv ON c.idCentroVaccinale=cv.idCentroVaccinale WHERE codicefiscale='" + user.getCodiceFiscale() + "'")
                        .executeQuery();
                rs.next();
                centrovaccinale = rs.getInt("cidCentroVaccinale");
                cittadino = rs.getInt("cidCittadino");
                user.setIdVaccinazione(1 + idVaccinazione);
                Statement statement = conn.createStatement();
                String table = "Vaccinazioni_" + centername.replace(" ", "_");
                int id;
                try {
                    statement.executeUpdate(
                            "CREATE TABLE " + table + " (" +
                                    "idVaccinazione integer PRIMARY KEY, " +
                                    "data_somministrazione date NOT NULL, " +
                                    "idCentroVaccinale smallint," +
                                    "idCittadino integer, " +
                                    "idTipologia smallint, " +
                                    "CONSTRAINT fk_CentriVaccinali FOREIGN KEY (idCentroVaccinale) REFERENCES CentriVaccinali(idCentroVaccinale), "
                                    +
                                    "CONSTRAINT fk_Cittadini FOREIGN KEY (idCittadino) REFERENCES Cittadini_Registrati(idCittadino), "
                                    +
                                    "CONSTRAINT fk_TipiEventi FOREIGN KEY (idTipologia) REFERENCES TipiVaccini(idTipologia))");
                    id = 1;
                    System.out.println("Creata " + table);
                } catch (Exception ex) {
                    output = ex.toString();
                    try {
                        statement.executeUpdate(
                                "INSERT INTO " + table
                                        + " (idVaccinazione, idCentroVaccinale, idCittadino, idTipologia, data_somministrazione) VALUES ("
                                        +
                                        user.getIdVaccinazione() + ", " +
                                        centrovaccinale + ", " +
                                        cittadino + ", " +
                                        user.getTipo().ordinal() + ", '" +
                                        user.getDataSomministrazione().split("/")[2] + "-"
                                        + user.getDataSomministrazione().split("/")[1] + "-"
                                        + user.getDataSomministrazione().split("/")[0] + "')");
                        System.out.println("Inserita vaccinazione in " + table);
                        idVaccinazione++;
                        try {
                            statement.executeUpdate(
                                    "UPDATE Cittadini_Registrati SET idVaccinazione=" + user.getIdVaccinazione() + " WHERE codicefiscale='" + user.getCodiceFiscale() + "'");
                            output = String.valueOf(idVaccinazione);
                        } catch (Exception ex2) {
                            System.out.println(ex2.toString());
                            output = ex2.toString();
                        }
                    } catch (Exception ex1) {
                        System.out.println("Dati inseriti errati");
                        System.out.println(ex1.getMessage());
                        output = ex1.toString();
                    }
                }
            }
            else{
                output="Cittadino già presente";
            }
        } catch (Exception e) {
            System.out.println(e);
            output = e.toString();
        }
        return output;
    }

    /**
     * Salva in una nuova riga di EventiAvversi l'evento avverso collegando l'id del cittadino e quello del centro vaccinale
     *
     * @param userid     Lo user id del cittadino che segnala l'evento avverso
     * @param centername Il nome del centro in cui è stata eseguita la vaccinazione che ha causato l'evento avverso
     * @param event      L'evento segnalato
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public String insertAdverseEvent(String userid, String centername, EventoAvverso event) {
        String output = "ok";
        try {
            try {
                ResultSet rs = conn.prepareStatement(
                                "SELECT idEvento FROM EventiAvversi e JOIN Cittadini_Registrati cr ON e.idCittadino=cr.idCittadino WHERE cr.userid='"
                                        + userid + "' AND e.idTipologia='" + event.getEvento().ordinal() + "'")
                        .executeQuery();
                rs.next();
                rs.getInt("idEvento");
                System.out.println("Evento avverso presente nel database");
            } catch (Exception ex) {
                output = ex.toString();
                int id;
                ResultSet rs = conn.prepareStatement("SELECT idEvento FROM EventiAvversi ORDER BY idEvento DESC")
                        .executeQuery();
                try {
                    rs.next();
                    id = rs.getInt("idEvento");
                } catch (Exception ex1) {
                    output = ex1.toString();
                    id = 0;
                }
                id++;
                Statement statement = conn.createStatement();
                int centrovaccinale;
                int cittadino;
                try {
                    rs = conn.prepareStatement(
                                    "SELECT idCentroVaccinale FROM CentriVaccinali WHERE nome='" + centername + "'")
                            .executeQuery();
                    rs.next();
                    centrovaccinale = rs.getInt("idCentroVaccinale");
                    rs = conn.prepareStatement(
                                    "SELECT idCittadino FROM Cittadini_Registrati WHERE userid='" + userid + "'")
                            .executeQuery();
                    rs.next();
                    cittadino = rs.getInt("idCittadino");
                    statement.executeUpdate(
                            "INSERT INTO EventiAvversi (idEvento, idCentroVaccinale, idCittadino, idTipologia, severità, note) VALUES ("
                                    +
                                    id + ", " +
                                    centrovaccinale + ", " +
                                    cittadino + ", " +
                                    event.getEvento().ordinal() + ", " +
                                    (int) event.getSeverita() + ", '" +
                                    event.getNote() + "')");
                    System.out.println("Inserito evento avverso");
                    output = "ok";
                } catch (Exception ex1) {
                    System.out.println("Dati inseriti errati");
                    output = ex1.toString();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            output = e.toString();
        }
        return output;
    }

    /**
     * Restituisce tutti i centri vaccinali presenti in CentriVaccinali
     *
     * @return I centri vaccinali presenti in CentriVaccinali
     */
    public ArrayList<CentroVaccinale> getCenters() {
        ArrayList<CentroVaccinale> output = new ArrayList<CentroVaccinale>();
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT " +
                                    "cv.nome AS cvnome, " +
                                    "cv.idTipologia AS cvidTipologia, " +
                                    "i.qualificatore AS iqualificatore, " +
                                    "i.nome AS inome, " +
                                    "i.n_civico AS in_civico, " +
                                    "com.nome AS comnome, " +
                                    "com.provincia AS comprovincia, " +
                                    "com.cap AS comcap " +
                                    "FROM CentriVaccinali cv JOIN (Indirizzi i JOIN Comuni com ON i.idComune=com.idComune) ON cv.idIndirizzo=i.idIndirizzo")
                    .executeQuery();
            while (rs.next()) {
                TipoCentroVaccinale tipo = null;
                int i = 0;
                for (TipoCentroVaccinale x : TipoCentroVaccinale.values()) {
                    if (i == rs.getInt("cvidTipologia")) {
                        tipo = x;
                        break;
                    }
                    i++;
                }
                output.add(
                        new CentroVaccinale(
                                rs.getString("cvnome"),
                                new Indirizzo(
                                        rs.getString("iqualificatore"),
                                        rs.getString("inome"),
                                        rs.getString("in_civico"),
                                        rs.getString("comnome"),
                                        rs.getString("comprovincia"),
                                        Integer.parseInt(rs.getString("comcap"))),
                                tipo));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce i centri vaccinali di CentriVaccinali di cui l'attributo nome sia uguale a quello passato come parametro
     *
     * @param name Il nome del centro da cercare
     * @return I centri vaccinali di cui l'attributo nome sia uguale a quello passato come parametro
     */
    public ArrayList<CentroVaccinale> getCentersByName(String name) {
        ArrayList<CentroVaccinale> output = new ArrayList<CentroVaccinale>();
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT " +
                                    "cv.nome AS cvnome, " +
                                    "cv.idTipologia AS cvidTipologia, " +
                                    "i.qualificatore AS iqualificatore, " +
                                    "i.nome AS inome, " +
                                    "i.n_civico AS in_civico, " +
                                    "com.nome AS comnome, " +
                                    "com.provincia AS comprovincia, " +
                                    "com.cap AS comcap " +
                                    "FROM CentriVaccinali cv JOIN (Indirizzi i JOIN Comuni com ON i.idComune=com.idComune) ON cv.idIndirizzo=i.idIndirizzo WHERE cv.nome LIKE '%"
                                    + name + "%'")
                    .executeQuery();
            while (rs.next()) {
                TipoCentroVaccinale tipo = null;
                int i = 0;
                for (TipoCentroVaccinale x : TipoCentroVaccinale.values()) {
                    if (i == rs.getInt("cvidTipologia")) {
                        tipo = x;
                        break;
                    }
                    i++;
                }
                output.add(
                        new CentroVaccinale(
                                rs.getString("cvnome"),
                                new Indirizzo(
                                        rs.getString("iqualificatore"),
                                        rs.getString("inome"),
                                        rs.getString("in_civico"),
                                        rs.getString("comnome"),
                                        rs.getString("comprovincia"),
                                        Integer.parseInt(rs.getString("comcap"))),
                                tipo));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce tutti i cittadini presenti nella tabella Cittadini_Registrati
     *
     * @return I cittadini presenti in Cittadini_Registrati
     */
    public ArrayList<Cittadino> getCitizens() {
        ArrayList<Cittadino> output = new ArrayList<Cittadino>();
        try {
            ResultSet rs = conn.prepareStatement(
                    "SELECT * FROM Cittadini_Registrati").executeQuery();
            while (rs.next()) {
                output.add(
                        new Cittadino(
                                rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("email"),
                                rs.getString("userid"), rs.getString("password"), 0, null, null));
            }
        } catch (Exception ex) {
            output = null;
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce tutti i cittadini presenti nella tabella Cittadini_Registrati di cui gli attributi nome e cognome siano uguali a quelli passati come parametro
     *
     * @param name    Il nome da cercare
     * @param surname Il cognome da cercare
     * @return I cittadini di cui il nome e il cognome sono uguali a quelli passati come parametro
     */
    public ArrayList<Cittadino> getCitizensByName(String name, String surname) {
        ArrayList<Cittadino> output = new ArrayList<Cittadino>();
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT * FROM Cittadini_Registrati WHERE nome LIKE '%" + name + "%' AND cognome LIKE '%" + surname
                                    + "%'")
                    .executeQuery();
            while (rs.next()) {
                output.add(
                        new Cittadino(
                                rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("email"),
                                rs.getString("userid"), rs.getString("password"), 0, null, null));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce tutti gli eventi avversi legati al centro vaccinale di cui il nome è passato come parametro
     *
     * @param centerName Il nome del centro di cui si vogliono cercare gli eventi avversi
     * @return Gli eventi avversi legati al centro vaccinale di cui il nome è passato come parametro
     */
    public ArrayList<EventoAvverso> getAdverseEvents(String centerName) {
        ArrayList<EventoAvverso> output = new ArrayList<EventoAvverso>();
        System.out.println("SELECT ev.idTipologia, AVG(ev.severità) AS avgseverità FROM EventiAvversi ev JOIN CentriVaccinali cv ON ev.idCentroVaccinale=cv.idCentroVaccinale WHERE cv.nome LIKE '%"
                + centerName + "%' GROUP BY ev.idTipologia");
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT ev.idTipologia, AVG(ev.severità) AS avgseverità FROM EventiAvversi ev JOIN CentriVaccinali cv ON ev.idCentroVaccinale=cv.idCentroVaccinale WHERE cv.nome LIKE '%"
                                    + centerName + "%' GROUP BY ev.idTipologia")
                    .executeQuery();
            while (rs.next()) {
                String note = "";
                TipoEventoAvverso tipo = null;
                int i = 0;
                for (TipoEventoAvverso x : TipoEventoAvverso.values()) {
                    if (i == rs.getInt("idTipologia")) {
                        tipo = x;
                        break;
                    }
                    i++;
                }
                try {
                    ResultSet rs_note = conn.prepareStatement("SELECT ev.note AS enote FROM EventiAvversi ev JOIN CentriVaccinali cv ON ev.idCentroVaccinale=cv.idCentroVaccinale WHERE cv.nome LIKE '%"
                            + centerName + "%' AND ev.idTipologia=" + tipo.ordinal()).executeQuery();
                    while (rs_note.next()) {
                        note += rs_note.getString("enote") + ";";
                        System.out.println(note);
                    }
                } catch (Exception ex1) {
                    System.out.println("SELECT ev.note AS enote FROM EventiAvversi ev JOIN CentriVaccinali cv ON ev.idCentroVaccinale=cv.idCentroVaccinale WHERE cv.nome LIKE '%"
                            + centerName + "%' AND ev.idTipologia=" + tipo.ordinal());
                    note = "";
                }
                output.add(
                        new EventoAvverso(
                                tipo,
                                Double.parseDouble(rs.getString("avgseverità")),
                                note));
            }
        } catch (Exception ex) {

            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce i cittadini vaccinati nel centro passato come parametro
     *
     * @param center Il centro di cui si vogliono trovare i cittadini
     * @return I cittadini vaccinati presenti nel centro passato come parametro
     */
    public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) {
        String table = "Vaccinazioni_" + center.getNomeCentro().replace(" ", "_");
        ArrayList<Cittadino> output = new ArrayList<Cittadino>();
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT * FROM Cittadini_Registrati cr JOIN " + table + " v ON cr.idCittadino=v.idCittadino")
                    .executeQuery();
            while (rs.next()) {
                TipoVaccino tipo = null;
                int i = 0;
                for (TipoVaccino x : TipoVaccino.values()) {
                    if (i == rs.getInt("idTipologia")) {
                        tipo = x;
                        break;
                    }
                    i++;
                }
                output.add(
                        new Cittadino(
                                rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("email"),
                                rs.getString("userid"), rs.getString("password"), rs.getLong("idVaccinazione"),
                                rs.getString("data_somministrazione").split("-")[2] + "/"
                                        + rs.getString("data_somministrazione").split("-")[1] + "/"
                                        + rs.getString("data_somministrazione").split("-")[0],
                                tipo));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce il cittadino di cui gli attributi userid e password sono uguali a quelli passati come parametro
     *
     * @param userid   Lo userid da cercare
     * @param password La password da cercare
     * @return Il cittadino il cui userid e password sono uguali a quelli passati come parametro
     */
    public synchronized Cittadino getCitizenByLogin(String userid, String password) {
        Cittadino output = null;
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT * FROM Cittadini_Registrati WHERE userid='" + userid + "' AND password='" + password + "'")
                    .executeQuery();
            if (rs.next()) {
                output = new Cittadino(
                        rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("email"),
                        rs.getString("userid"),
                        rs.getString("password"), 0, null, null);
            } else {
                System.out.println("centrivaccinali.Cittadino non presente");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce i centri vaccinali il cui comune e il tipo siano uguali a quelli passati come parametro
     *
     * @param comune Il nome del comune da cercare
     * @param tipo   Il tipo del centro vaccinale da cercare
     * @return I centri vaccinali il cui nome e tipo sono uguali a quelli passati come parametro
     */
    public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo) {
        ArrayList<CentroVaccinale> output = new ArrayList<CentroVaccinale>();
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT " +
                                    "cv.nome AS cvnome, " +
                                    "cv.idTipologia AS cvidTipologia, " +
                                    "i.qualificatore AS iqualificatore, " +
                                    "i.nome AS inome, " +
                                    "i.n_civico AS in_civico, " +
                                    "com.nome AS comnome, " +
                                    "com.provincia AS comprovincia, " +
                                    "com.cap AS comcap " +
                                    "FROM CentriVaccinali cv JOIN (Indirizzi i JOIN Comuni com ON i.idComune=com.idComune) ON cv.idIndirizzo=i.idIndirizzo WHERE com.nome LIKE '%"
                                    + comune + "%' AND cv.idTipologia=" + tipo.ordinal())
                    .executeQuery();
            while (rs.next()) {
                output.add(
                        new CentroVaccinale(
                                rs.getString("cvnome"),
                                new Indirizzo(
                                        rs.getString("iqualificatore"),
                                        rs.getString("inome"),
                                        rs.getString("in_civico"),
                                        rs.getString("comnome"),
                                        rs.getString("comprovincia"),
                                        Integer.parseInt(rs.getString("comcap"))),
                                tipo));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Restituisce il centro vaccinale in cui è presente il cittadino passato come parametro
     *
     * @param user Il cittadino da cercare
     * @return Il centro vaccinale in cui è presente il cittadino passato come parametro
     */
    public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) {
        CentroVaccinale output = null;
        try {
            boolean present = false;
            ResultSet rs = null;
            String table;
            for (CentroVaccinale x : getCenters()) {
                present = false;
                table = "Vaccinazioni_" + x.getNomeCentro().replace(" ", "_");
                try {
                    rs = conn.prepareStatement(
                                    "SELECT * FROM " + table)
                            .executeQuery();
                    rs.next();
                    int temp = rs.getInt("idVaccinazione");
                    present = true;
                } catch (Exception ex) {
                }
                if (present) {
                    try {
                        rs = conn.prepareStatement(
                                        "SELECT " +
                                                "cv.nome AS cvnome, " +
                                                "cv.idTipologia AS cvidTipologia, " +
                                                "i.qualificatore AS iqualificatore, " +
                                                "i.nome AS inome, " +
                                                "i.n_civico AS in_civico, " +
                                                "com.nome AS comnome, " +
                                                "com.provincia AS comprovincia, " +
                                                "com.cap AS comcap " +
                                                "FROM Comuni com JOIN (Indirizzi i JOIN (CentriVaccinali cv JOIN (" + table
                                                + " v JOIN Cittadini_Registrati cr ON v.idCittadino=cr.idCittadino) ON cv.idCentroVaccinale=v.idCentroVaccinale) ON i.idIndirizzo=cv.idIndirizzo) ON com.idComune=i.idComune WHERE cr.userid='"
                                                + user.getUserid() + "'")
                                .executeQuery();
                        if (rs.next()) {
                            TipoCentroVaccinale tipo = null;
                            int i = 0;
                            for (TipoCentroVaccinale y : TipoCentroVaccinale.values()) {
                                if (i == rs.getInt("cvidTipologia")) {
                                    tipo = y;
                                    break;
                                }
                                i++;
                            }
                            output = new CentroVaccinale(
                                    rs.getString("cvnome"),
                                    new Indirizzo(
                                            rs.getString("iqualificatore"),
                                            rs.getString("inome"),
                                            rs.getString("in_civico"),
                                            rs.getString("comnome"),
                                            rs.getString("comprovincia"),
                                            Integer.parseInt(rs.getString("comcap"))),
                                    tipo);
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output;
    }

    /**
     * Riempe il database con i dati presenti nei file di utils
     *
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public String fillDataBase() {
        String output = "ok";
        String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "serverCV"
                + System.getProperty("file.separator") + "src" +
                System.getProperty("file.separator") + "main" +
                System.getProperty("file.separator") + "java" +
                System.getProperty("file.separator") + "utils" +
                System.getProperty("file.separator");
        String file, query;
        Statement statement;
        try {
            statement = conn.createStatement();
            file = path + "Districts.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO COMUNI VALUES(" +
                                    temp.split(";")[0] + ", '" +
                                    temp.split(";")[1] + "', '" +
                                    temp.split(";")[2] + "', '" +
                                    temp.split(";")[3] + "')";
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolata tabella Comuni");

        try {
            statement = conn.createStatement();
            file = path + "Addresses.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO Indirizzi VALUES (" +
                                    temp.split(";")[0] + ", '" +
                                    temp.split(";")[1] + "', '" +
                                    temp.split(";")[2] + "', '" +
                                    temp.split(";")[3] + "', " +
                                    temp.split(";")[4] + ")";
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolata tabella Indirizzi");

        ArrayList<String> nomi = new ArrayList<String>();
        try {
            statement = conn.createStatement();
            file = path + "Centers.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO CentriVaccinali VALUES(" +
                                    temp.split(";")[0] + ", '" +
                                    temp.split(";")[1] + "', " +
                                    temp.split(";")[2] + ", " +
                                    temp.split(";")[3] + ")";
                    statement.executeUpdate(query);
                    nomi.add(temp.split(";")[1]);
                    query = "CREATE TABLE Vaccinazioni_" + temp.split(";")[1].replace(" ", "_") + " (" +
                            "idVaccinazione integer PRIMARY KEY, " +
                            "data_somministrazione date NOT NULL, " +
                            "idCentroVaccinale smallint," +
                            "idCittadino integer, " +
                            "idTipologia smallint, " +
                            "CONSTRAINT fk_CentriVaccinali FOREIGN KEY (idCentroVaccinale) REFERENCES CentriVaccinali(idCentroVaccinale), "
                            +
                            "CONSTRAINT fk_Cittadini FOREIGN KEY (idCittadino) REFERENCES Cittadini_Registrati(idCittadino), "
                            +
                            "CONSTRAINT fk_TipiEventi FOREIGN KEY (idTipologia) REFERENCES TipiVaccini(idTipologia))";
                    System.out.println(query);
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolata tabella CentriVaccinali");

        try {
            statement = conn.createStatement();
            file = path + "Citizens.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO Cittadini_Registrati (idCittadino, nome, cognome, email, userid, password, codicefiscale, idCentroVaccinale) VALUES(" +
                                    temp.split(";")[0] + ", '" +
                                    temp.split(";")[1] + "', '" +
                                    temp.split(";")[2] + "', '" +
                                    temp.split(";")[3] + "', '" +
                                    temp.split(";")[4] + "', '" +
                                    EncryptData.encrypt(temp.split(";")[5]) + "', '" +
                                    temp.split(";")[6] + "', " +
                                    temp.split(";")[7] + ")";
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolata tabella Cittadini_Registrati");

        try {
            statement = conn.createStatement();
            file = path + "Vaccinations.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO Vaccinazioni_" + nomi.get(Integer.parseInt(temp.split(";")[2]) - 1).replace(" ", "_") + " VALUES (" +
                                    temp.split(";")[0] + ", '" +
                                    temp.split(";")[1] + "', " +
                                    temp.split(";")[2] + ", " +
                                    temp.split(";")[3] + ", " +
                                    temp.split(";")[4] + ")";
                    statement.executeUpdate(query);
                    query =
                            "UPDATE Cittadini_Registrati SET idVaccinazione=" + temp.split(";")[0] + " WHERE idCittadino=" + temp.split(";")[3];
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolate le tabelle delle vaccinazioni");

        try {
            statement = conn.createStatement();
            file = path + "AdverseEvents.csv";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
                String temp = br.readLine();
                while (temp != null) {
                    query =
                            "INSERT INTO EventiAvversi VALUES(" +
                                    temp.split(";")[0] + ", " +
                                    temp.split(";")[1] + ", '" +
                                    temp.split(";")[2] + "', " +
                                    temp.split(";")[3] + ", " +
                                    temp.split(";")[4] + ", " +
                                    temp.split(";")[5] + ")";
                    statement.executeUpdate(query);
                    temp = br.readLine();
                }
                br.close();
            } catch (UnsupportedEncodingException e) {
                System.out.println(e);
                output = e.toString();
            } catch (IOException e) {
                System.out.println(e);
                output = e.toString();
            }
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
            output = ex1.toString();
        }
        System.out.println("Popolata la tabella EventiAvversi");
        output = "ok";
        return output;
    }

    /**
     * Restituisce il cittadino il cui id della vaccinazione sia uguale a quello presente come parametro
     *
     * @param id L'id della vaccinazione da cercare
     * @return Il cittadino il cui id della vaccinazione sia uguale a quello presente come parametro
     */
    public Cittadino getCitizenByVaccinationID(int id) {
        Cittadino output = null;
        try {
            ResultSet rs = conn.prepareStatement(
                            "SELECT * FROM Cittadini_Registrati WHERE idVaccinazione=" + id)
                    .executeQuery();
            if (rs.next()) {
                output = new Cittadino(
                        rs.getString("nome"), rs.getString("cognome"), rs.getString("codicefiscale"), rs.getString("email"),
                        rs.getString("userid"),
                        rs.getString("password"), rs.getInt("idVaccinazione"), null, null);
            } else {
                System.out.println("centrivaccinali.Cittadino non presente");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }

    /**
     * Controlla se lo user id passato come parametro è già presente nel database
     *
     * @param userid Lo user id da cercare
     * @return true se lo user id è presente false altrimenti
     */
    public boolean checkUserIDPresence(String userid) {
        boolean output;
        try {
            ResultSet rs = conn
                    .prepareStatement(
                            "SELECT idCittadino FROM Cittadini_Registrati WHERE userid='" + userid + "'")
                    .executeQuery();
            rs.next();
            rs.getInt("idCittadino");
            output = true;
        } catch (Exception ex) {
            output = false;
        }
        return output;
    }

    /**
     * Controlla se l'email passata come parametro è già presente nel database
     *
     * @param email L'email da cercare
     * @return true se l'email è presente false altrimenti
     */
    public boolean checkEmailPresence(String email) {
        boolean output;
        try {
            ResultSet rs = conn
                    .prepareStatement(
                            "SELECT idCittadino FROM Cittadini_Registrati WHERE email='" + email + "'")
                    .executeQuery();
            rs.next();
            rs.getInt("idCittadino");
            output = true;
        } catch (Exception ex) {
            output = false;
        }
        return output;
    }

    /**
     * Aggiorna i campi del cittadino registrato il cui codice fiscale sia uguale a quello passato come parametro
     *
     * @param user I dati del cittadino da aggiornare
     * @return Il codice dell'eccezione in caso l'operazione non sia andata a buon fine o "ok"
     */
    public synchronized String updateCitizen(Cittadino user) {
        String output = "ok";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = conn
                    .prepareStatement("SELECT idCittadino, idCentroVaccinale FROM Cittadini_Registrati WHERE codicefiscale='" + user.getCodiceFiscale() + "'")
                    .executeQuery();
            System.out.println("SELECT idCittadino, idCentroVaccinale FROM Cittadini_Registrati WHERE codicefiscale='" + user.getCodiceFiscale() + "'");
            int id;
            int centrovaccinale;
            rs.next();
            id = rs.getInt("idCittadino");
            centrovaccinale = rs.getInt("idCentroVaccinale");
            statement.executeUpdate(
                "UPDATE Cittadini_Registrati SET "
                +
                "email='" +
                user.getEmail() + "', userid='" +
                user.getUserid() + "', nome='"+
                user.getNome()+"', cognome='"+
                user.getCognome()+"', "+
                "password='" + user.getPassword() + "' WHERE codicefiscale='" +
                user.getCodiceFiscale() + "'");
            System.out.println("Aggiornato cittadino");
            output = "ok";
        } catch (Exception e) {
            System.out.println(e);
            output = e.toString();
        }
        return output;
    }

    /**
     * Ottiene l'ultimo id delle vaccinazioni
     */
    private void getLastId() {
        try {
            ResultSet rs = conn.prepareStatement(
                    "SELECT idVaccinazione FROM Cittadini_Registrati ORDER BY (idVaccinazione) DESC"
            ).executeQuery();
            rs.next();
            idVaccinazione = rs.getInt("idVaccinazione");
        } catch (Exception ex1) {
            System.out.println(ex1.toString());
        }
    }
    
    /**
     * Controlla se il codice fiscale è presente nel DB
     *
     * @param codice Il codice da cercare nel database
     * @return true se è presente false in caso contrario
     */
    private boolean checkCodiceFiscale(String codice){
        boolean output;
        try {
            ResultSet rs = conn
                    .prepareStatement(
                            "SELECT idCittadino FROM Cittadini_Registrati WHERE codicefiscale='" + codice + "'")
                    .executeQuery();
            rs.next();
            rs.getInt("idCittadino");
            output=true;
        } catch (Exception ex) {
            output=false;
        }
        return output;
    }
}
