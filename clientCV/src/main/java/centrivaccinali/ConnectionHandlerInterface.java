// autori: Gaudiano Antonio 744102 VA, Bonaldo Samuele 744054 VA, Costantini Mirko 744982 VA

package centrivaccinali;

import enums.TipoCentroVaccinale;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * <p>Interfaccia ConnectionHandlerInterface.</p>
 */
public interface ConnectionHandlerInterface extends Remote {

    /**
     * <p>registerCitizen.</p>
     *
     * @param user a {@link centrivaccinali.Cittadino} object
     * @return a {@link java.lang.String} object
     * @throws java.rmi.RemoteException if any.
     */
    public String registerCitizen(Cittadino user) throws RemoteException;

    /**
     * <p>registerCenter.</p>
     *
     * @param center a {@link centrivaccinali.CentroVaccinale} object
     * @return a {@link java.lang.String} object
     * @throws java.rmi.RemoteException if any.
     */
    public String registerCenter(CentroVaccinale center) throws RemoteException;

    /**
     * <p>registerVaccination.</p>
     *
     * @param user a {@link centrivaccinali.Cittadino} object
     * @param centername a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     * @throws java.rmi.RemoteException if any.
     */
    public String registerVaccination(Cittadino user, String centername) throws RemoteException;

    /**
     * <p>insertAdverseEvent.</p>
     *
     * @param userid a {@link java.lang.String} object
     * @param centerName a {@link java.lang.String} object
     * @param event a {@link centrivaccinali.EventoAvverso} object
     * @return a {@link java.lang.String} object
     * @throws java.rmi.RemoteException if any.
     */
    public String insertAdverseEvent(String userid, String centerName, EventoAvverso event) throws RemoteException;

    /**
     * <p>getCenters.</p>
     *
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<CentroVaccinale> getCenters() throws RemoteException;

    /**
     * <p>getCentersByName.</p>
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<CentroVaccinale> getCentersByName(String name) throws RemoteException;

    /**
     * <p>getCitizensByName.</p>
     *
     * @param name a {@link java.lang.String} object
     * @param surname a {@link java.lang.String} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<Cittadino> getCitizensByName(String name, String surname) throws RemoteException;

    /**
     * <p>getAdverseEvents.</p>
     *
     * @param centerName a {@link java.lang.String} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<EventoAvverso> getAdverseEvents(String centerName) throws RemoteException;

    /**
     * <p>getCitizens.</p>
     *
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<Cittadino> getCitizens() throws RemoteException;

    /**
     * <p>getVaccinatedCitizens.</p>
     *
     * @param center a {@link centrivaccinali.CentroVaccinale} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<Cittadino> getVaccinatedCitizens(CentroVaccinale center) throws RemoteException;

    /**
     * <p>getCitizenByLogin.</p>
     *
     * @param userid a {@link java.lang.String} object
     * @param password a {@link java.lang.String} object
     * @return a {@link centrivaccinali.Cittadino} object
     * @throws java.rmi.RemoteException if any.
     */
    public Cittadino getCitizenByLogin(String userid, String password) throws RemoteException;

    /**
     * <p>getCenterByPlaceAndType.</p>
     *
     * @param comune a {@link java.lang.String} object
     * @param tipo a {@link enums.TipoCentroVaccinale} object
     * @return a {@link java.util.ArrayList} object
     * @throws java.rmi.RemoteException if any.
     */
    public ArrayList<CentroVaccinale> getCenterByPlaceAndType(String comune, TipoCentroVaccinale tipo) throws RemoteException;

    /**
     * <p>getCenterByVaccinatedCitizen.</p>
     *
     * @param user a {@link centrivaccinali.Cittadino} object
     * @return a {@link centrivaccinali.CentroVaccinale} object
     * @throws java.rmi.RemoteException if any.
     */
    public CentroVaccinale getCenterByVaccinatedCitizen(Cittadino user) throws RemoteException;

    /**
     * <p>getCitizenByVaccinationID.</p>
     *
     * @param id a int
     * @return a {@link centrivaccinali.Cittadino} object
     * @throws java.rmi.RemoteException if any.
     */
    public Cittadino getCitizenByVaccinationID(int id) throws RemoteException;

    /**
     * <p>checkUserIDPresence.</p>
     *
     * @param username a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean checkUserIDPresence(String username);

    /**
     * <p>checkEmailPresence.</p>
     *
     * @param email a {@link java.lang.String} object
     * @return a boolean
     */
    public boolean checkEmailPresence(String email);

    public String updateCitizen(Cittadino user) throws RemoteException;
}
