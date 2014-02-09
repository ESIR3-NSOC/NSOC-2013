package esir.dom13.nsoc.adminDatabasePeople;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 11:28
 * To change this template use File | Settings | File Templates.
 */
public interface IadminDatabasePeople {
    public void addPeople(String surname,String firstname, String id_people, String id_rfid,String promo, String options, String specialite, String emailAddress, String statut, String administrator);
    public void deletePeople(String firstname, String surname);
    public void setId_people(String id_people, String firstname, String surname);
    public void setPromo(String promo, String firstname, String surname) ;
    public void setOptions(String options, String firstname, String surname);
    public void setSpecialite(String specialite, String firstname, String surname);
    public void setEmailAddress(String emailAddress, String firstname, String surname);
    public void setAdmin(String admin, String firstname, String surname);
    public String getFirstname(String statut);
    public String getSurname(String statut);
}
