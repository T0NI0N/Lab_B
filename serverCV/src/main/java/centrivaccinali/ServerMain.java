package centrivaccinali;// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe main del server
 */
public class ServerMain {
    private static Registry registry;
    private static DatabaseHandler obj;

    /**
     * Crea l'istanza di centrivaccinali.DatabaseHandler e la pubblica sul registry alla porta di
     * default
     * 
     * @param args argomenti passati da riga di comando
     */
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.print("Inserisci l'host del database: ");
            String host = reader.readLine();
            System.out.print("Inserisci l'utente del database: ");
            String user = reader.readLine();
            System.out.print("Inserisci la password del database: ");
            String password = reader.readLine();

            obj = new DatabaseHandler(host, user, password);
            ConnectionHandlerInterface stub = (ConnectionHandlerInterface) UnicastRemoteObject.exportObject(obj, 0);

            System.out.println("Creazione registry...");
            registry = LocateRegistry.createRegistry(1099);

            System.out.println("Pubblicazione del servizio...");
            registry.rebind("dbconnection", stub);

            System.out.println("Server pronto e in esecuzione");

        } catch (Exception e) {
            System.out.print("Server error: ");
            e.printStackTrace();
        }
    }
}
