package esir.dom13.nsoc.adminDatabaseRoom;

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
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */

@Provides({
        @ProvidedPort(name = "setDatabaseRoom", type = PortType.SERVICE, className = IadminDatabaseRoom.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabaseRoom extends AbstractComponentType implements IadminDatabaseRoom {

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabaseRoom", method = "addRoom")
    @Override
    public void addRoom(String nameRoom, String id_Building, String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "INSERT INTO `idatabaseroom` (`nameRoom`, `id_Building`, `nameEquipment`)" +
                " VALUES ('" +  nameRoom + "', '" + id_Building + "', '" + nameEquipment + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseRoom", method = "deleteRoom")
    @Override
    public void deleteRoom(String nameRoom, String nameBuilding) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabaseroom` WHERE nameRoom = '"+ nameRoom + "' AND nameBuilding = '" + nameBuilding +"')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }


    //voir avec Yvan selon la construction de la base de données
    @Port(name = "setDatabaseRoom", method = "setId_building")
    @Override
    public void setId_building(String nameRoom, String id_Building) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "UPDATE `idatabaseroom` SET `id_Building` = '" + id_Building +"' WHERE nameRoom = '" + nameRoom + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseRoom", method = "setNameEquipment")
    @Override
    public void setNameEquipment(String nameBuilding, String nameRoom, String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
    String sql = "UPDATE `idatabaseroom` SET `nameEquipment` = '" + nameEquipment +"' WHERE nameRoom = '" + nameRoom + "' AND nameBuilding = '" + nameBuilding + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseRoom", method = "getName")
    @Override
    public String getName(String nameBuilding){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `nameRoom` FROM `idatabaseroom` WHERE nameBuilding = '" + nameBuilding + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("nameRoom");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabaseRoom", method = "getSalle")
    @Override
    public String getSalle(String nameBuilding, String nameSalle){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT * FROM `idatabaseroom` WHERE nameRoom = '" + nameSalle + "' AND nameBuilding = '" + nameBuilding + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            value = rs.getString("nameEquipment");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
        }
        catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
