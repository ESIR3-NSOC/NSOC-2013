package esir.dom13.nsoc.adminDatabaseRoom;

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
 * Time: 21:30
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "setDatabaseRoom", type = PortType.SERVICE, className = IadminDatabaseRoom.class)
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
public class adminDatabaseRoom extends AbstractComponentType implements IadminDatabaseRoom {


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


    @Port(name = "setDatabaseRoom", method = "addRoom")
    @Override
    public void addRoom(String nameRoom, String nameBuilding, String id_Building, String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "INSERT INTO `idatabaseroom` (`nameRoom`, `nameBuilding`, `id_Building`, `nameEquipment`)" +
                " VALUES ('" +  nameRoom + "', '" + nameBuilding + "', '" + id_Building + "', '" + nameEquipment + "')";
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


    @Port(name = "setDatabaseRoom", method = "deleteRoom")
    @Override
    public void deleteRoom(String nameRoom, String nameBuilding) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "DELETE FROM `idatabaseroom` WHERE nameRoom = '"+ nameRoom + "' AND nameBuilding = '" + nameBuilding +"'";
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



    //voir avec Yvan selon la construction de la base de données
    @Port(name = "setDatabaseRoom", method = "setId_building")
    @Override
    public void setId_building(String nameRoom, String id_Building) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "UPDATE `idatabaseroom` SET `id_Building` = '" + id_Building +"' WHERE nameRoom = '" + nameRoom + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
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

    @Port(name = "setDatabaseRoom", method = "setNameEquipment")
    @Override
    public void setNameEquipment(String nameBuilding, String nameRoom, String nameEquipment) {
        //To change body of implemented methods use File | Settings | File Templates.
    String request = "UPDATE `idatabaseroom` SET `nameEquipment` = '" + nameEquipment +"' WHERE nameRoom = '" + nameRoom + "' AND nameBuilding = '" + nameBuilding + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
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

    @Port(name = "setDatabaseRoom", method = "getName")
    @Override
    public String getName(String nameBuilding){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `nameRoom` FROM `idatabaseroom` WHERE nameBuilding = '" + nameBuilding + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);



            while (rs.next()){
                value = rs.getString("nameRoom");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
            stmt.close();
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
        String request = "SELECT * FROM `idatabaseroom` WHERE nameRoom = '" + nameSalle + "' AND nameBuilding = '" + nameBuilding + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);

            while(rs.next()){

            value = rs.getString("nameEquipment");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            }
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
