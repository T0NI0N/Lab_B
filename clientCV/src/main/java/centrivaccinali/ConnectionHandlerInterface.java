// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import enums.TipoCentroVaccinale;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/** Interfaccia ConnectionHandlerInterface. */
public interface ConnectionHandlerInterface extends Remote {

  /**
   * registerCitizen.
   *
   * @param user a {@link centrivaccinali.Cittadino} object
   * @param centername the centername
   * @return a {@link java.lang.String} object
   * @throws RemoteException the remote exception
   */
  public String registerCitizen(Cittadino user, String centername) throws RemoteException;

  /**
   * registerCenter.
   *
   * @param center a {@link centrivaccinali.CentroVaccinale} object
   * @return a {@link java.lang.String} object
   * @throws RemoteException the remote exception
   */
  public String registerCenter(CentroVaccinale center) throws RemoteException;

  /**
   * registerVaccination.
   *
   * @param user a {@link centrivaccinali.Cittadino} object
   * @param centername a {@link java.lang.String} object
   * @return a {@link java.lang.String} object
   * @throws RemoteException the remote exception
   */
  public String registerVaccination(Cittadino user, String centername) throws RemoteException;

  /**
   * insertAdverseEvent.
   *
   * @param userid a {@link java.lang.String} object
   * @param centerName a {@link java.lang.String} object
   * @param event a {@link centrivaccinali.EventoAvverso} object
   * @return a {@link java.lang.String} object
   * @throws RemoteException the remote exception
   */
  public String insertAdverseEvent(String userid, String centerName, EventoAvverso event)
      throws RemoteException;

  /**
   * getCenters.
   *
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCenters() throws RemoteException;

  /**
   * getCentersByName.
   *
   * @param name a {@link java.lang.String} object
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException;

  /**
   * getCitizensByName.
   *
   * @param name a {@link java.lang.String} object
   * @param surname a {@link java.lang.String} object
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException;

  /**
   * getAdverseEvents.
   *
   * @param centerName a {@link java.lang.String} object
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<EventoAvverso> getAdverseEvents(String centerName) throws RemoteException;

  /**
   * getCitizens.
   *
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getCitizens() throws RemoteException;

  /**
   * getVaccinatedCitizens.
   *
   * @param center a {@link centrivaccinali.CentroVaccinale} object
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException;

  /**
   * getCitizenByLogin.
   *
   * @param userid a {@link java.lang.String} object
   * @param password a {@link java.lang.String} object
   * @return a {@link centrivaccinali.Cittadino} object
   * @throws RemoteException the remote exception
   */
  public Cittadino getCitizenByLogin(String userid, String password) throws RemoteException;

  /**
   * getCenterByPlaceAndType.
   *
   * @param comune a {@link java.lang.String} object
   * @param tipo a {@link enums.TipoCentroVaccinale} object
   * @return a {@link java.util.ArrayList} object
   * @throws RemoteException the remote exception
   */
  public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo)
      throws RemoteException;

  /**
   * getCenterByVaccinatedCitizen.
   *
   * @param user a {@link centrivaccinali.Cittadino} object
   * @return a {@link centrivaccinali.CentroVaccinale} object
   * @throws RemoteException the remote exception
   */
  public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) throws RemoteException;

  /**
   * getCitizenByVaccinationID.
   *
   * @param id a int
   * @return a {@link centrivaccinali.Cittadino} object
   * @throws RemoteException the remote exception
   */
  public Cittadino getCitizenByVaccinationID(int id) throws RemoteException;

  /**
   * checkUserIDPresence.
   *
   * @param username a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean checkUserIDPresence(String username);

  /**
   * checkEmailPresence.
   *
   * @param email a {@link java.lang.String} object
   * @return a boolean
   */
  public boolean checkEmailPresence(String email);

  /**
   * updateCitizen>.
   *
   * @param user the user
   * @return the string
   * @throws RemoteException the remote exception
   */
  public String updateCitizen(Cittadino user) throws RemoteException;
}
