package esir.dom13.nsoc.adminDatabaseEquipment;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.*;

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

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false)
})

@ComponentType
public class adminDatabaseEquipment extends AbstractComponentType implements IadminDatabaseEquipment {


    // JDBC driver name and database URL
    private String JDBC_DRIVER, DB_URL;

    //  Database credentials
    private String USER, PASS;
    private Connection connection;


    @Start
    public void start() {
        JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
        DB_URL = getDictionary().get("DB_URL").toString();
        USER = getDictionary().get("USER").toString();
        PASS = getDictionary().get("PASS").toString();
        //STEP 2: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            Log.debug("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Log.debug("Connecting to database...  OK");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Stop
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Update
    public void update() {
        JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
        DB_URL = getDictionary().get("DB_URL").toString();
        USER = getDictionary().get("USER").toString();
        PASS = getDictionary().get("PASS").toString();

        try {
            connection.close();
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            Log.debug("Connecting to database ::: IDatabasePeople");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Port(name = "setDatabaseEquipment", method = "addEquipment")
    @Override
    public void addEquipment(String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "INSERT INTO `idatabaseequipment` (`nameEquipment`) VALUES ('" +  nameEquipment + "')";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            stmt.executeUpdate(request);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Port(name = "setDatabaseEquipment", method = "deleteEquipment")
    @Override
    public void deleteEquipment(String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "DELETE FROM `idatabaseequipment` WHERE nameEquipment = '"+ nameEquipment + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            stmt.executeUpdate(request);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Port(name = "setDatabaseEquipment", method = "getNameEquipment")
    @Override
    public String getNameEquipment(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `nameEquipment` FROM `idatabaseequipment`";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);

            while (rs.next()){
               value = rs.getString("nameEquipment");
               tableau.put(value);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
