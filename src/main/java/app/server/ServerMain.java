package app.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
    private static Registry registry;

    public static void main(String[] args) {
        try {
            System.out.println("Creating stub...");
            DatabaseHandler obj = new DatabaseHandler();
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
