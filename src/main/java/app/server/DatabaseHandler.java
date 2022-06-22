package app.server;

public class DatabaseHandler implements ConnectionHandlerInterface{

    public DatabaseHandler() {
        System.out.println("Creazione / connessione al db...");
    }

    public void login(){
        System.out.println("logging in...");
    }

    public void register(){
        System.out.println("register user...");
    }

}
