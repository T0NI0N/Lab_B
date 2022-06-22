package app.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectionHandlerInterface extends Remote {

    public void login() throws RemoteException;

    public void register() throws RemoteException;
}
