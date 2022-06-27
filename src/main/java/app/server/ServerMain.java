package app.server;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.ConsoleHandler;

import app.TipoCentroVaccinale;
import app.TipoEventoAvverso;
import app.TipoVaccino;
import app.client.centrivaccinali.CentroVaccinale;
import app.client.centrivaccinali.EventoAvverso;
import app.client.centrivaccinali.Indirizzo;
import app.client.cittadini.Cittadino;

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
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.println("Creating stub...");
            System.out.print("Inserisci l'host del database (default=localhost):");
            String host1=reader.readLine();
            if(!host1.isBlank())
            {
                host=host1;
            }
            System.out.print("Inserisci l'utente del database (default=ospite):");
            String user1=reader.readLine();
            if(!user1.isBlank())
            {
                user=user1;
            }
            System.out.print("Inserisci la password del database (default=DefaultUserPassword):");
            String password1=reader.readLine();
            if(!password1.isBlank())
            {
                password=password1;
            }
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
