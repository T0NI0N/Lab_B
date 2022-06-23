package app.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import app.cittadini.Cittadino;

public interface ConnectionHandlerInterface extends Remote {

    public boolean login() throws RemoteException;

    public void register(Cittadino user) throws RemoteException;
}
