package esir.dom13.nsoc.adminDatabaseEquipment;

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
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "setDatabaseEquipment", type = PortType.SERVICE, className = IadminDatabaseEquipment.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabaseEquipment extends AbstractComponentType implements IadminDatabaseEquipment {

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabaseEquipment", method = "addEquipment")
    @Override
    public void addEquipment(String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "INSERT INTO `idatabaseequipment` (`nameEquipment`) VALUES ('" +  nameEquipment + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseEquipment", method = "deleteEquipment")
    @Override
    public void deleteEquipment(String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabaseequipment` WHERE nameEquipment = '"+ nameEquipment + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseEquipment", method = "getNameEquipment")
    @Override
    public String getNameEquipment(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `nameEquipment` FROM `idatabaseequipment`";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
               value = rs.getString("nameEquipment");
               tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
