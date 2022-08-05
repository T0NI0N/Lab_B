// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import cittadini.Cittadino;
import enums.TipoCentroVaccinale;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


/**
 * Classe che gestisce la connessione al registry del client e l'uso del
 * servizio pubblicato sul registry stesso.
 * 
 * Implementata con l'ausilio del pattern singleton
 */
public class ClientConnectionHandler {

	// required for singleton pattern
	private static ClientConnectionHandler handler;

	private String defaultHostAddress = "localhost";
	private int defaultHostPort = 1099;

	private String hostAddress;
	private int hostPort;
	private final String serverServiceName = "dbconnection";

	private Registry registry;

	private ConnectionHandlerInterface stub = null;

	private Cittadino loggedUser;

	/**
	 * Costuttore privato per il pattern singleton.
	 * Definisce l'host e la porta per la connessione al registry.
	 */
	private ClientConnectionHandler() {
		hostAddress = defaultHostAddress;
		hostPort = defaultHostPort;
	}

	/**
	 * Classe implementata con pattern singleton, questo metodo è necessario per
	 * recuperare l'istanza di ClientConnectionHandler
	 * 
	 * @return l'istanza di ClientConnectionHandler
	 */
	public static ClientConnectionHandler getClientConnectionHandler() {
		if (handler == null)
			handler = new ClientConnectionHandler();

		return handler;
	}

	/**
	 * Stabilisce la connessione al registry oppure ritorna false in caso di errori.
	 */
	public boolean connect() {
		try {
			registry = LocateRegistry.getRegistry(hostAddress, hostPort);
			stub = (ConnectionHandlerInterface) registry.lookup(serverServiceName);
			System.out.println("Connection established correctly");
		} catch (Exception e) {
			System.out.println("Connection error: " + e.toString());
			return false;
		}
		return true;
	}

	/**
	 * Registra un nuovo utente
	 * 
	 * @param user l'utente da registrare
	 * @throws RemoteException
	 */
	public void registerCitizen(Cittadino user) throws RemoteException {
		stub.registerCitizen(user);
	}

	/*
	 * registra un centro vaccinale
	 * 
	 * @param center il centro da inserire
	 * 
	 * @throws RemoteException
	 */
	public void registerCenter(CentroVaccinale center) throws RemoteException {
		stub.registerCenter(center);
	}

	/*
	 * crea la connessione tra un cittadino e un centro vaccinale
	 * 
	 * @param user il cittadino a cui è stata fatta la vaccinazione
	 * 
	 * @param center il centro da cui è stata fatta la vaccinazione
	 * 
	 * @throws RemoteException
	 */
	public void registerVaccination(Cittadino user, CentroVaccinale center) throws RemoteException {
		stub.registerVaccination(user, center);
	}

	/*
	 * inserisce un evento avverso
	 * 
	 * @param citizen il cittadino che ha segnalato l'evento avverso
	 * 
	 * @param center il centro su cui è stato segnalato l'evento avverso
	 * 
	 * @param event l'evento segnalato dal cittadino
	 * 
	 * @throws RemoteException
	 */
	public void insertAdverseEvent(Cittadino citizen, CentroVaccinale center, EventoAvverso event)
			throws RemoteException {
		stub.insertAdverseEvent(citizen, center, event);
	}

	/*
	 * ottiene tutti i centri vaccinali presenti nella tabella CentriVaccinali
	 * 
	 * @return i centri vaccinali registrati nel database
	 */
	public ArrayList<CentroVaccinale> getCenters() throws RemoteException {
		return stub.getCenters();
	}

	/*
	 * ottiene i centri vaccinali con il nome che contiene il parametro passato
	 * 
	 * @param name il nome del centro ricercato
	 * 
	 * @return i centri vaccinali con un nome che contiene il parametro passato
	 */
	public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException {
		return stub.getCentersByName(name);
	}

	/*
	 * restituisce i cittadini con un nome e un cognome che contengono i parametri
	 * passati
	 * 
	 * @param name il nome del cittadino
	 * 
	 * @param surname il cognome del cittadino
	 * 
	 * @return i cittadini con il nome e il congnome che contengono i parametri
	 * passati
	 */
	public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException {
		return getCitizensByName(name, surname);
	}

	/*
	 * restituisce gli eventi avversi collegati al centro vaccinale passato come
	 * parametro
	 * 
	 * @param center il centro di cui si vogliono ottenere i parametri
	 * 
	 * @return gli eventi avversi collegati al centro passato in input
	 */
	public ArrayList<EventoAvverso> getAdverseEvents(String centerName) throws RemoteException {
		return stub.getAdverseEvents(centerName);
	}

	/*
	 * restituisce tutti i cittadini presenti nella tabella Cittadini_REgistrati
	 * 
	 * @return i cittadini presenti nel database
	 */
	public ArrayList<Cittadino> getCitizens() throws RemoteException {
		return stub.getCitizens();
	}

	/*
	 * restituisce tutti i cittadini vaccinati nel centro passato come parametro
	 * 
	 * @param center il centro di cui si vogliono ottenere i cittadini registrati
	 * 
	 * @return i cittadni vaccinati nel centro passato in input
	 */
	public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException {
		return stub.getVaccinatedCitizens(center);
	}

	/*
	 * ottiene il cittadino che esegue il login mediante userid e password
	 * 
	 * @param userid lo userid del cittadino che vuole fare l'accesso
	 * 
	 * @param password la password del cittadino che vuole fare l'accesso
	 * 
	 * @return il cittadino per cui lo userid e la password sono corrette
	 */
	public Cittadino getCitizenByLogin(String userid, String password) throws RemoteException {
		return stub.getCitizenByLogin(userid, password);
	}

	/*
	 * restituisce i centri vaccinali presenti nel comune indicato in input e che
	 * sono della tipologia inserita mediante parametro
	 * 
	 * @param comune il comune in cui si trova il centro vaccinale
	 * 
	 * @param tipo il tipo che deve essere il centro vaccinale
	 */
	public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo)
			throws RemoteException {
		return stub.getCenterByPlaceAndType(comune, tipo);
	}

	/*
	 * restituisce il centro in cui è stato vaccinato il cittadino
	 * 
	 * @param user il cittadino per cui si cerca il centro
	 * 
	 * @return il centro qualora sia presente
	 */
	public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) throws RemoteException {
		return stub.getCenterByVaccinatedCitizen(user);
	}

	// TODO scrivere la documentazione di login
	public boolean login(String userid, String password) throws RemoteException {

		boolean success = false;

		try {
			loggedUser = stub.getCitizenByLogin(userid, password);
			success = (loggedUser != null);
		} catch (Exception e) {
			return false;
		}

		return success;
	}

	/**
	 * Controlla se è stato effettuato l'accesso
	 * 
	 * @return true se l'accesso è stato effettuato, false altrimenti
	 */
	public boolean isLogged() {
		return loggedUser != null;
	}

	/**
	 * Ritorna l'istanza dell'utente che ha effettuato il login
	 * 
	 * @return l'utente che ha effettuato l'accesso
	 */
	public Cittadino getLoggedUser() {
		return loggedUser;
	}

	/**
	 * Disconnette il client dal server.
	 */
	public void disconnect() {
		stub = null;
		loggedUser = null;
	}

	/**
	 * Ritorna il cittadino vaccinato con id vaccinazione dato, se presente.
	 * @param id id da verificare
	 * @return true se è presente un cittadino con id in input, false altrimenti.
	 */
	public boolean getCitizenByVaccinationID(int id) {
		return stub.getCitizenByVaccinationID(id);
	}

	/**
	 * Controlla se il nome utente dato è già stato utilizzato.
	 * @param username il nome utente da controllare
	 * @return true se il nome utente è già stato utilizzato, false altrimenti
	 */
	public boolean checkUserIDPresence(String username) {
		return stub.checkUserIDPresence(username);
	}

	/**
	 * Controlla se l'email data è già stata utilizzata.
	 * @param email l'email da controllare
	 * @return true se l'email è già stata utilizzata, false altrimenti
	 */
	public boolean checkEmailPresence(String email) {
		return stub.checkEmailPresence(email);
	}
}
