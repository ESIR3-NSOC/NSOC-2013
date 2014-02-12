package esir.dom13.nsoc.adminDatabaseBuilding;

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
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "setDatabaseBuilding", type = PortType.SERVICE, className = IadminDatabaseBuilding.class)
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
public class adminDatabaseBuilding extends AbstractComponentType implements IadminDatabaseBuilding {


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

    @Port(name = "setDatabaseBuilding", method = "addBuilding")
    @Override
    public void addBuilding(String id_building, String nameBuilding, String numberOfRoom) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "INSERT INTO `idatabasebuilding` (`id_building`, `nameBuilding`, `numberOfRoom`)" +
                " VALUES ('" +  id_building + "', '" + nameBuilding + "', '" + numberOfRoom + "')";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            stmt.executeUpdate(request);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Port(name = "setDatabaseBuilding", method = "deleteBuilding")
    @Override
    public void deleteBuilding(String nameBuilding) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "DELETE FROM `idatabasebuilding` WHERE nameBuilding = '"+ nameBuilding + "'";
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

    @Port(name = "setDatabaseBuilding", method = "setId_building")
    @Override
    public void setId_building(String nameBuilding, String id_building) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "UPDATE `idatabasebuilding` SET `id_building` = '" + id_building +"' WHERE nameBuilding = '" + nameBuilding + "'";
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

    @Port(name = "setDatabaseBuilding", method = "setNumberOfRoom")
    @Override
    public void setNumberOfRoom(String nameBuilding, String numberOfRoom) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "UPDATE `idatabasebuilding` SET `numberOfRoom` = '" + numberOfRoom +"' WHERE nameBuilding = '" + nameBuilding + "'";
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

    @Port(name = "setDatabaseBuilding", method = "getNameBuilding")
    @Override
    public String getNameBuilding(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `nameBuilding` FROM `idatabasebuilding`";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);


            while (rs.next()){
                value = rs.getString("nameBuilding");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabaseBuilding", method = "getIdBuilding")
    @Override
    public String getIdBuilding(String nameBuilding){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `id_building` FROM `idatabasebuilding` WHERE nameBuilding = '"+ nameBuilding + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);


            while (rs.next()){
                value = rs.getString("id_building");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabaseBuilding", method = "getBuilding")
    @Override
    public String getBuilding(String nameBuilding){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT * FROM `idatabasebuilding` WHERE nameBuilding = '" + nameBuilding + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);

            while(rs.next()){
            value = rs.getString("id_building");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
                value = rs.getString("numberOfRoom");   //voir si cela fonctionne et qu'on récupère valeur Bdd
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
