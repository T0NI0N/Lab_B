package centrivaccinali;// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

import enums.TipoCentroVaccinale;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

/** The interface Connection handler interface. */
public interface ConnectionHandlerInterface extends Remote {

  /**
   * Register citizen string.
   *
   * @param user the user
   * @param centername the centername
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String registerCitizen(Cittadino user, String centername) throws RemoteException;

  /**
   * Register center string.
   *
   * @param center the center
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String registerCenter(CentroVaccinale center) throws RemoteException;

  /**
   * Register vaccination string.
   *
   * @param user the user
   * @param centername the centername
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String registerVaccination(Cittadino user, String centername) throws RemoteException;

  /**
   * Insert adverse event string.
   *
   * @param userid the userid
   * @param centername the centername
   * @param event the event
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String insertAdverseEvent(String userid, String centername, EventoAvverso event)
      throws RemoteException;

  /**
   * Gets centers.
   *
   * @return the centers
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCenters() throws RemoteException;

  /**
   * Gets centers by name.
   *
   * @param name the name
   * @return the centers by name
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException;

  /**
   * Gets citizens by name.
   *
   * @param name the name
   * @param surname the surname
   * @return the citizens by name
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException;

  /**
   * Gets adverse events.
   *
   * @param centerName the center name
   * @return the adverse events
   * @throws RemoteException the remote exception
   */
  public ArrayList<EventoAvverso> getAdverseEvents(String centerName) throws RemoteException;

  /**
   * Gets citizens.
   *
   * @return the citizens
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getCitizens() throws RemoteException;

  /**
   * Gets vaccinated citizens.
   *
   * @param center the center
   * @return the vaccinated citizens
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException;

  /**
   * Gets citizen by login.
   *
   * @param userid the userid
   * @param password the password
   * @return the citizen by login
   * @throws RemoteException the remote exception
   */
  public Cittadino getCitizenByLogin(String userid, String password) throws RemoteException;

  /**
   * Gets center by place and type.
   *
   * @param comune the comune
   * @param tipo the tipo
   * @return the center by place and type
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo)
      throws RemoteException;

  /**
   * Gets center by vaccinated citizen.
   *
   * @param user the user
   * @return the center by vaccinated citizen
   * @throws RemoteException the remote exception
   */
  public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) throws RemoteException;

  /**
   * Gets citizen by vaccination id.
   *
   * @param id the id
   * @return the citizen by vaccination id
   * @throws RemoteException the remote exception
   */
  public Cittadino getCitizenByVaccinationID(int id) throws RemoteException;

  /**
   * Check user id presence boolean.
   *
   * @param userid the userid
   * @return the boolean
   * @throws RemoteException the remote exception
   */
  public boolean checkUserIDPresence(String userid) throws RemoteException;

  /**
   * Check email presence boolean.
   *
   * @param email the email
   * @return the boolean
   * @throws RemoteException the remote exception
   */
  public boolean checkEmailPresence(String email) throws RemoteException;

  /**
   * Update citizen string.
   *
   * @param user the user
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String updateCitizen(Cittadino user) throws RemoteException;
}
