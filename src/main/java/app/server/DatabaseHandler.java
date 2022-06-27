package app.server;
import java.sql.*;
import java.util.ArrayList;
import app.TipoCentroVaccinale;
import app.TipoEventoAvverso;
import app.TipoVaccino;
import app.client.centrivaccinali.CentroVaccinale;
import app.client.centrivaccinali.EventoAvverso;
import app.client.centrivaccinali.Indirizzo;
import app.client.cittadini.Cittadino;
public class DatabaseHandler implements ConnectionHandlerInterface {
    private String connection = "jdbc:postgresql://";
    private Connection conn;
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
                                "email varchar(50) NOT NULL, " +
                                "userid varchar(20) NOT NULL, " +
                                "password varchar(100) NOT NULL)");
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
            }
            System.out.println("Connesso al database...");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public boolean login() {
        System.out.println("logging in...");
        return true;
    }
    public void registerCitizen(Cittadino user) {
        try {
            try {
                ResultSet rs = conn
                        .prepareStatement(
                                "SELECT idCittadino FROM Cittadini_Registrati WHERE userid='" + user.getUserid() + "'")
                        .executeQuery();
                rs.next();
                rs.getInt("idCittadino");
                System.out.println("User id presente nel database");
            } catch (Exception ex) {
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
                }
                id++;
                statement.executeUpdate(
                        "INSERT INTO Cittadini_Registrati (idCittadino, nome, cognome, email, userid, password) VALUES ("
                                +
                                id + ", '" +
                                user.getNome() + "', '" +
                                user.getCognome() + "', '" +
                                user.getEmail() + "', '" +
                                user.getUserid() + "', '" +
                                user.getPassword() + "')");
                System.out.println("Inserito cittadino");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void registerCenter(CentroVaccinale center) {
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
            } catch (Exception ex) {
                check = true;
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
                        try {
                            rs = conn.prepareStatement("SELECT idIndirizzo FROM Indirizzi ORDER BY idIndirizzo DESC")
                                    .executeQuery();
                            rs.next();
                            indirizzo = rs.getInt("idIndirizzo");
                        } catch (Exception ex2) {
                            indirizzo = 0;
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
                            try {
                                rs = conn.prepareStatement("SELECT idComune FROM Comuni ORDER BY idComune DESC")
                                        .executeQuery();
                                rs.next();
                                comune = rs.getInt("idComune");
                            } catch (Exception ex3) {
                                comune = 0;
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
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void registerVaccination(Cittadino user, CentroVaccinale center) {
        try {
            Statement statement = conn.createStatement();
            String table = "Vaccinazioni_" + center.getNomeCentro();
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
                ResultSet rs = conn
                        .prepareStatement("SELECT idVaccinazione FROM " + table + " ORDER BY idVaccinazione DESC")
                        .executeQuery();
                try {
                    rs.next();
                    id = rs.getInt("idVaccinazione");
                } catch (Exception ex1) {
                    id = 0;
                }
                id++;
            }
            try {
                ResultSet rs = conn.prepareStatement("SELECT idVaccinazione FROM " + table
                        + " v JOIN Cittadini_Registrati cr ON v.idCittadino=cr.idCittadino WHERE cr.userid='"
                        + user.getUserid() + "'").executeQuery();
                rs.next();
                rs.getInt("idVaccinazione");
                System.out.println("User id presente nel database");
            } catch (Exception ex) {
                int centrovaccinale;
                int cittadino;
                try {
                    ResultSet rs = conn.prepareStatement(
                            "SELECT idCentroVaccinale FROM CentriVaccinali WHERE nome='" + center.getNomeCentro() + "'")
                            .executeQuery();
                    rs.next();
                    centrovaccinale = rs.getInt("idCentroVaccinale");
                    rs = conn.prepareStatement(
                            "SELECT idCittadino FROM Cittadini_Registrati WHERE userid='" + user.getUserid() + "'")
                            .executeQuery();
                    rs.next();
                    cittadino = rs.getInt("idCittadino");
                    statement.executeUpdate(
                            "INSERT INTO " + table
                                    + " (idVaccinazione, idCentroVaccinale, idCittadino, idTipologia, data_somministrazione) VALUES ("
                                    +
                                    id + ", " +
                                    centrovaccinale + ", " +
                                    cittadino + ", " +
                                    user.getTipo().ordinal() + ", '" +
                                    user.getDataSomministrazione().split("/")[2] + "-"
                                    + user.getDataSomministrazione().split("/")[1] + "-"
                                    + user.getDataSomministrazione().split("/")[0] + "')");
                    System.out.println("Inserita vaccinazione in " + table);
                } catch (Exception ex1) {
                    System.out.println("Dati inseriti errati");
                    ;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void insertAdverseEvent(Cittadino citizen, CentroVaccinale center, EventoAvverso event) {
        try {
            try {
                ResultSet rs = conn.prepareStatement(
                        "SELECT idEvento FROM EventiAvversi e JOIN Cittadini_Registrati cr ON e.idCittadino=cr.idCittadino WHERE cr.userid='"
                                + citizen.getUserid() + "' AND e.idTipologia='" + event.getEvento().ordinal() + "'")
                        .executeQuery();
                rs.next();
                rs.getInt("idEvento");
                System.out.println("Evento avverso presente nel database");
            } catch (Exception ex) {
                int id;
                ResultSet rs = conn.prepareStatement("SELECT idEvento FROM EventiAvversi ORDER BY idEvento DESC")
                        .executeQuery();
                try {
                    rs.next();
                    id = rs.getInt("idEvento");
                } catch (Exception ex1) {
                    id = 0;
                }
                id++;
                Statement statement = conn.createStatement();
                int centrovaccinale;
                int cittadino;
                try {
                    rs = conn.prepareStatement(
                            "SELECT idCentroVaccinale FROM CentriVaccinali WHERE nome='" + center.getNomeCentro() + "'")
                            .executeQuery();
                    rs.next();
                    centrovaccinale = rs.getInt("idCentroVaccinale");
                    rs = conn.prepareStatement(
                            "SELECT idCittadino FROM Cittadini_Registrati WHERE userid='" + citizen.getUserid() + "'")
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
                                    event.getNote() + "')"
                    );
                    System.out.println("Inserito evento avverso");
                } catch (Exception ex1) {
                    System.out.println("Dati inseriti errati");
                    ;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
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
    public ArrayList<Cittadino> getCitizens() {
        ArrayList<Cittadino> output = new ArrayList<Cittadino>();
        try {
            ResultSet rs = conn.prepareStatement(
                    "SELECT * FROM Cittadini_Registrati").executeQuery();
            while (rs.next()) {
                output.add(
                        new Cittadino(
                                rs.getString("nome"), rs.getString("cognome"), "", rs.getString("email"),
                                rs.getString("userid"), rs.getString("password"), 0, null, null));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }
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
                                rs.getString("nome"), rs.getString("cognome"), "", rs.getString("email"),
                                rs.getString("userid"), rs.getString("password"), 0, null, null));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }
    public ArrayList<EventoAvverso> getAdverseEvents(String centerName) {
        ArrayList<EventoAvverso> output = new ArrayList<EventoAvverso>();
        try {
            ResultSet rs = conn.prepareStatement(
                    "SELECT ev.idTipologia, AVG(ev.severità) AS avgseverità FROM EventiAvversi ev JOIN CentriVaccinali cv ON ev.idCentroVaccinale=cv.idCentroVaccinale WHERE cv.nome LIKE '%"
                            + centerName + "%' GROUP BY ev.idTipologia")
                    .executeQuery();
            while (rs.next()) {
                TipoEventoAvverso tipo = null;
                int i = 0;
                for (TipoEventoAvverso x : TipoEventoAvverso.values()) {
                    if (i == rs.getInt("idTipologia")) {
                        tipo = x;
                        break;
                    }
                    i++;
                }
                output.add(
                        new EventoAvverso(
                                tipo,
                                Double.parseDouble(rs.getString("avgseverità")),
                                ""));
            }
        } catch (Exception ex) {
            
            System.out.println(ex);
        }
        return output;
    }
    public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) {
        String table = "Vaccinazioni_" + center.getNomeCentro();
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
                                rs.getString("nome"), rs.getString("cognome"), "", rs.getString("email"),
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
    public Cittadino getCitizenByLogin(String userid, String password) {
        Cittadino output = null;
        try {
            ResultSet rs = conn.prepareStatement(
                    "SELECT * FROM Cittadini_Registrati WHERE userid='" + userid + "' AND password='" + password + "'")
                    .executeQuery();
            if(rs.next(){
                output = new Cittadino(
                rs.getString("nome"), rs.getString("cognome"), "", rs.getString("email"), rs.getString("userid"),
                rs.getString("password"), 0, null, null);
            }
            else{
                System.out.println("Cittadino non presente");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return output;
    }
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
    public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) {
        CentroVaccinale output = null;
        try {
            boolean present = false;
            ResultSet rs = null;
            String table;
            for (CentroVaccinale x : getCenters()) {
                present = false;
                table = "Vaccinazioni_" + x.getNomeCentro();
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
}
