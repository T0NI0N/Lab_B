package app.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
    public static void main(String[] args) {
        try {

            DatabaseHandler obj = new DatabaseHandler();
            ConnectionHandlerInterface stub = (ConnectionHandlerInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("Balancer", stub);
            System.out.println("Serverready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
        }
    }
}
