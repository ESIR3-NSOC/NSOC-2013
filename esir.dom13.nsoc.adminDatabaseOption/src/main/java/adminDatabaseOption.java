import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 04/02/14
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
@Provides({
        @ProvidedPort(name = "setDatabaseOption", type = PortType.SERVICE, className = IadminDatabaseOption.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabaseOption extends AbstractComponentType implements IadminDatabaseOption{

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabaseOption",method = "addOption")

    public void addOption(String option, String specialite)  {
        String sql = "INSERT INTO `IDatabaseOption` (`options`, `specialite`)" +
                " VALUES ('" +  option + "', '" + specialite + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseOption",method = "deleteOption")
    @Override
    public void deleteOption(String option, String specialite) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `IDatabaseOption` WHERE options = '"+ option + "' AND specialite = '" + specialite + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

}
