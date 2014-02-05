import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 05/02/14
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
@Provides({
        @ProvidedPort(name = "setDatabaseStatut", type = PortType.SERVICE, className = IadminDatabaseStatut.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabaseStatut extends AbstractComponentType implements IadminDatabaseStatut {

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabaseStatut", method = "addStatut")
    @Override
    public void addStatut(String statut) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "INSERT INTO `IDatabaseStatut` (`statut`) VALUES ('" +  statut + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseStatut", method = "deleteStatut")
    @Override
    public void deleteStatut(String statut) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `IDatabaseStatut` WHERE statut = '"+ statut + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }
}

