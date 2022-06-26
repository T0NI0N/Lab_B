package app.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import app.CentriVaccinali.CentroVaccinale;
import app.CentriVaccinali.EventoAvverso;
import app.cittadini.Cittadino;

import java.util.*;

public interface ConnectionHandlerInterface extends Remote {

    public boolean login() throws RemoteException;

    public void registerCitizen(Cittadino user) throws RemoteException;

    public void registerCenter(CentroVaccinale center) throws RemoteException;

    public void registerVaccination(Cittadino user, CentroVaccinale center) throws RemoteException;

    public void insertAdverseEvent(Cittadino citizen, CentroVaccinale center, EventoAvverso event)
            throws RemoteException;

    public ArrayList<CentroVaccinale> getCenters() throws RemoteException;

    public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException;

    public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException;

    public ArrayList<EventoAvverso> getAdverseEvents(CentroVaccinale center) throws RemoteException;

    public ArrayList<Cittadino> getCitizens() throws RemoteException;

    public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException;

}
