package app.server;

import app.cittadini.Cittadino;

public class DatabaseHandler implements ConnectionHandlerInterface{

    public DatabaseHandler() {
        System.out.println("Creazione / connessione al db...");
    }

    public boolean login(){
        System.out.println("logging in...");
        return true;
    }

    public void register(Cittadino user){
        System.out.println("register user...");
    }

}
