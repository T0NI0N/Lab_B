package app;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import app.cittadini.Cittadino;
import app.server.ConnectionHandlerInterface;

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

	public void register(Cittadino user) throws RemoteException {
		stub.register(user);
	}

	public boolean login() throws RemoteException {
		return stub.login();
	}

	public boolean isLogged() {
		return loggedUser != null;
	}

	public Cittadino getLoggedUser() {
		return loggedUser;
	}

}
