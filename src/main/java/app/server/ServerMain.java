package app.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import app.TipoCentroVaccinale;
import app.TipoEventoAvverso;
import app.TipoVaccino;
import app.CentriVaccinali.CentroVaccinale;
import app.CentriVaccinali.EventoAvverso;
import app.CentriVaccinali.Indirizzo;
import app.cittadini.Cittadino;

public class ServerMain {

    private static String host = "localhost";
    private static String user = "ospite";
    private static String password = "DefaultUserPassword";
    private static Registry registry;
    private static DatabaseHandler obj;

    /**
     * Crea l'istanza di DatabaseHandler e la pubblica sul registry alla porta di
     * default
     * 
     * @param args argomenti passati da riga di comando
     */
    public static void main(String[] args) {
        try {
            System.out.println("Creating stub...");
            // System.out.print("Inserisci l'host del database:");
            // String host=System.console().readLine();
            // System.out.print("Inserisci l'utente del database:");
            // String user=System.console().readLine();
            // System.out.print("Inserisci la password del database:");
            // String password=System.console().readLine();
            obj = new DatabaseHandler(host, user, password);
            ConnectionHandlerInterface stub = (ConnectionHandlerInterface) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println("Creating registry...");
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("Binding service...");
            registry.rebind("dbconnection", stub);
            System.out.println("Server ready and running");
        } catch (Exception e) {
            System.out.print("Server error: ");
            e.printStackTrace();
        }
    }
}
