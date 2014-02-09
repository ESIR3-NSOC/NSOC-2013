package esir.dom13.nsoc.adminDatabaseOption;

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
public class adminDatabaseOption extends AbstractComponentType implements IadminDatabaseOption {

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

    public void addSpecialite(String option, String specialite)  {
        String sql = "INSERT INTO `idatabaseoption` (`options`, `specialite`)" +
                " VALUES ('" +  option + "', '" + specialite + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    public void addOption(String option)  {

        String specialite = "";
        String sql = "INSERT INTO `idatabaseoption` (`options`, `specialite`)" +
                " VALUES ('" +  option + "', '" + specialite + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseOption",method = "deleteOption")
    @Override
    public void deleteOption(String option) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabaseoption` WHERE options = '"+ option + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseOption",method = "deleteSpecialite")
    @Override
    public void deleteSpecialite(String specialite) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabaseoption` WHERE specialite = '"+ specialite + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseOption", method = "getOption")
    @Override
    public String getOption(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `options` FROM `idatabaseoption`";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("options");
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabaseOption", method = "getSpecialite")
    @Override
    public String getSpecialite(String option){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `specialite` FROM `idatabaseoption` WHERE options = '"+ option +"'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("specialite");
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

}
