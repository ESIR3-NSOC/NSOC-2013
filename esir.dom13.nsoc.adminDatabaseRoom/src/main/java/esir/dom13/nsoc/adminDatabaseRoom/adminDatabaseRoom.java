package esir.dom13.nsoc.adminDatabaseRoom;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

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
    public void deleteRoom(String nameRoom) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabaseroom` WHERE nameRoom = '"+ nameRoom + "')";
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
    public void setNameEquipment(String nameRoom, String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "UPDATE `idatabaseroom` SET `nameEquipment` = '" + nameEquipment +"' WHERE nameRoom = '" + nameRoom + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }
}
