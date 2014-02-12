package esir.dom13.nsoc.adminDatabasePeople;

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
 * Time: 09:41
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "setDatabasePeople", type = PortType.SERVICE, className = IadminDatabasePeople.class)
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
public class adminDatabasePeople extends AbstractComponentType implements IadminDatabasePeople {


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

    @Port(name = "setDatabasePeople",method = "addPeople")
    @Override
    public void addPeople(String surname,String firstname, String id_people, String id_rfid,String promo, String options, String specialite, String emailAddress, String statut, String administrator)  {
        String request = "INSERT INTO `idatabasepeople` (`id_people`, `id_rfid`, `firstname`, `surname`, `promo`, `options`, `specialite`, `emailAddress`, `statut`, `administrator`)" +
                " VALUES ('" +  id_people + "', '" + id_rfid + "', '" + firstname + "', '" + surname + "', '" + promo + "', '" + options + "', '" + specialite + "', '" + emailAddress + "', '" + statut + "', '" + administrator + "')";
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

    @Port(name = "setDatabasePeople",method = "deletePeople")
    @Override
    public void deletePeople(String firstname, String surname) {
        //To change body of implemented methods use File | Settings | File Templates.
        String request = "DELETE FROM `idatabasepeople` WHERE firstname = '"+ firstname + "' AND surname = '" + surname + "'";
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


    @Port(name = "setDatabasePeople",method = "setId_people")
    public void setId_people(String id_people, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `id_people` = '" + id_people +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setId_badge")
    public void setId_badge(String id_badge, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `id_rfid` = '" + id_badge +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setPromo")
    public void setPromo(String promo , String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `promo` = '" + promo +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setOptions")
    public void setOptions(String options, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `options` = '" + options +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setSpecialite")
    public void setSpecialite(String specialite, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `specialite` = '" + specialite +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setEmailAddress")
     public void setEmailAddress(String emailAddress, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `emailAddress` = '" + emailAddress +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople",method = "setAdmin")
    public void setAdmin(String admin, String firstname, String surname){
        String request = "UPDATE `idatabasepeople` SET `administrator` = '" + admin +"' WHERE firstname = '" + firstname +"' AND surname = '" + surname + "'";
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

    @Port(name = "setDatabasePeople", method = "getSurname")
    @Override
    public String getSurname(String statut){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `surname` FROM `idatabasepeople` WHERE statut = '" + statut + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs =  stmt.executeQuery(request);

            while (rs.next()){
                value = rs.getString("surname");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getFirstname")
    @Override
    public String getFirstname(String statut){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `firstname` FROM `idatabasepeople` WHERE statut = '" + statut + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);

            while (rs.next()){
                value = rs.getString("firstname");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getID")
    @Override
    public String getID(String nom, String prenom){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT `id_people` FROM `idatabasepeople` WHERE firstname = '" + prenom + "' AND surname = '"+nom+"'";
Log.debug(request);
Statement stmt = null;

try {
        //STEP 4: Execute a query
        Log.debug("Creating statement...");
stmt = connection.createStatement();
Log.debug("Execution of :: "+request);

   ResultSet rs = stmt.executeQuery(request);

            while (rs.next()){
                value = rs.getString("id_people");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabasePeople", method = "getPeople")
    @Override
    public String getPeople(String nom , String prenom){

        String value = null;
        JSONArray tableau = new JSONArray();
        String request = "SELECT * FROM `idatabasepeople` WHERE firstname = '" + prenom + "' AND surname = '"+ nom +"'";
Log.debug(request);
Statement stmt = null;

try {
        //STEP 4: Execute a query
        Log.debug("Creating statement...");
stmt = connection.createStatement();
Log.debug("Execution of :: "+request);

ResultSet rs = stmt.executeQuery(request);

            while(rs.next()){
            value = rs.getString("id_people");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("id_rfid");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("promo");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("options");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("specialite");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("emailAddress");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            value = rs.getString("administrator");   //voir si cela fonctionne et qu'on récupère valeur Bdd
            tableau.put(value);
            }
        stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
