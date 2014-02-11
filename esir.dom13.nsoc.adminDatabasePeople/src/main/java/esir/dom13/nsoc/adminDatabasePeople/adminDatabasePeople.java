package esir.dom13.nsoc.adminDatabasePeople;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 09:41
 * To change this template use File | Settings | File Templates.
 */

@Provides({
        @ProvidedPort(name = "setDatabasePeople", type = PortType.SERVICE, className = IadminDatabasePeople.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabasePeople extends AbstractComponentType   implements IadminDatabasePeople {

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabasePeople",method = "addPeople")
    @Override
    public void addPeople(String surname,String firstname, String id_people, String id_rfid,String promo, String options, String specialite, String emailAddress, String statut, String administrator)  {
        String sql = "INSERT INTO `idatabasepeople` (`id_people`, `id_rfid`, `firstname`, `surname`, `promo`, `options`, `specialite`, `emailAddress`, `statut`, `administrator`)" +
                " VALUES ('" +  id_people + "', '" + id_rfid + "', '" + firstname + "', '" + surname + "', '" + promo + "', '" + options + "', '" + specialite + "', '" + emailAddress + "', '" + statut + "', '" + administrator + "')";
    CachedRowSetImpl rs = null;
    rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "deletePeople")
    @Override
    public void deletePeople(String firstname, String surname) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabasepeople` WHERE firstname = '"+ firstname + "' AND surname = '" + surname + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }


    @Port(name = "setDatabasePeople",method = "setId_people")
    public void setId_people(String id_people, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `id_people` = '" + id_people +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setId_badge")
    public void setId_badge(String id_badge, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `id_rfid` = '" + id_badge +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setPromo")
    public void setPromo(String promo , String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `promo` = '" + promo +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setOptions")
    public void setOptions(String options, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `options` = '" + options +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setSpecialite")
    public void setSpecialite(String specialite, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `specialite` = '" + specialite +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setEmailAddress")
     public void setEmailAddress(String emailAddress, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `emailAddress` = '" + emailAddress +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople",method = "setAdmin")
    public void setAdmin(String admin, String firstname, String surname){
        String sql = "UPDATE `idatabasepeople` SET `administrator` = '" + admin +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePeople", method = "getSurname")
    @Override
    public String getSurname(String statut){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `surname` FROM `idatabasepeople` WHERE statut = '" + statut + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("surname");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getFirstname")
    @Override
    public String getFirstname(String statut){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `firstname` FROM `idatabasepeople` WHERE statut = '" + statut + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("firstname");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getID")
    @Override
    public String getID(String nom, String prenom){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `id_people` FROM `idatabasepeople` WHERE firstname = '" + prenom + "' AND surname = '"+nom+"'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("id_people");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getPeople")
    @Override
    public String getPeople(String nom , String prenom){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT * FROM `idatabasepeople` WHERE firstname = '" + prenom + "' AND surname = '"+ nom +"'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while(rs.next()){
            value = rs.getString("id_people");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("id_rfid");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("promo");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("options");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("specialite");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("emailAddress");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("administrator");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
