package esir.dom13.nsoc.adminDatabasePromo;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 04/02/14
 * Time: 18:25
 * To change this template use File | Settings | File Templates.
 */
@Provides({
        @ProvidedPort(name = "setDatabasePromo", type = PortType.SERVICE, className = IadminDatabasePromo.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabasePromo extends AbstractComponentType implements IadminDatabasePromo {

    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabasePromo", method = "addPromo")
    @Override
    public void addPromo(String namePromo) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "INSERT INTO `IDatabasePromo` (`promo`) VALUES ('" +  namePromo + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePromo", method = "deletePromo")
    @Override
    public void deletePromo(String namePromo) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `IDatabasePromo` WHERE promo = '"+ namePromo + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabasePromo", method = "getPromo")
    @Override
    public String getPromo(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `promo` FROM `idatabasepromo`";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("promo");
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
