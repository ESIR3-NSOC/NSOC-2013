import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 22:08
 * To change this template use File | Settings | File Templates.
 */

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
}
