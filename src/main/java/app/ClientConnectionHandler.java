package app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import app.CentriVaccinali.CentroVaccinale;
import app.CentriVaccinali.EventoAvverso;
import app.cittadini.Cittadino;
import app.server.ConnectionHandlerInterface;

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
	 * Stabilisce la connessione al registry.
	 * @return true se la connessione è andata a buon fine, false altrimanti.
	 */
	public boolean connect() {
		boolean available = true;

		try {
			registry = LocateRegistry.getRegistry(hostAddress, hostPort);
			stub = (ConnectionHandlerInterface) registry.lookup(serverServiceName);
			System.out.println("Connection established correctly");
		} catch (Exception e) {
			System.out.println("Connection error: " + e.toString());
			available = false;
		}

		return available;
	}

	/**
	 * Registra un nuovo utente 
	 * @param user l'utente da registrare
	 * @throws RemoteException
	 */
	public void registerCitizen(Cittadino user) throws RemoteException {
		stub.registerCitizen(user);
	}
	
	//TODO inserire la documentazione
    public void registerCenter(CentroVaccinale center) throws RemoteException{
		stub.registerCenter(center);
	}

    public void registerVaccination(Cittadino user, CentroVaccinale center) throws RemoteException{
		stub.registerVaccination(user, center);
	}

    public void insertAdverseEvent(Cittadino citizen, CentroVaccinale center, EventoAvverso event) throws RemoteException{
		stub.insertAdverseEvent(citizen, center, event);
	}

    public ArrayList<CentroVaccinale> getCenters() throws RemoteException{
		return stub.getCenters();
	}

    public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException{
		return stub.getCentersByName(name);
	}

    public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException{
		return getCitizensByName(name, surname);
	}

    public ArrayList<EventoAvverso> getAdverseEvents(CentroVaccinale center) throws RemoteException{
		return stub.getAdverseEvents(center);
	}

    public ArrayList<Cittadino> getCitizens() throws RemoteException{
		return stub.getCitizens();
	}

    public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException{
		return stub.getVaccinatedCitizens(center);
	}

    public Cittadino getCitizenByLogin(String userid, String password) throws RemoteException{
		return stub.getCitizenByLogin(userid, password);
	}

    public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo) throws RemoteException{
		return stub.getCenterByPlaceAndType(comune, tipo);
	}

    public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) throws RemoteException{
		return stub.getCenterByVaccinatedCitizen(user);
	}

	public boolean login() throws RemoteException {
		return stub.login();
	}

	/**
	 * Controlla se è stato effettuato l'accesso
	 * @return true se l'accesso è stato effettuato, false altrimenti
	 */
	public boolean isLogged() {
		return loggedUser != null;
	}

	/**
	 * Ritorna l'istanza dell'utente che ha effettuato il login
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

}
