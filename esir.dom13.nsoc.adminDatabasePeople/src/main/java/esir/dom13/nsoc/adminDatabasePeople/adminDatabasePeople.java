package esir.dom13.nsoc.adminDatabasePeople;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

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

    public void addPeople(String id_people, String id_rfid, String firstname, String surname, String promo, String options, String specialite, String emailAddress, String statut, String administrator)  {
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

}
