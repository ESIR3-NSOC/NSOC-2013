package esir.dom13.nsoc.databasePeople;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import com.mysql.jdbc.Driver;
import java.sql.*;
import java.sql.DriverManager;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "getCursus", type = PortType.SERVICE, className = IDatabasePeople.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false)
})


@ComponentType
public class DatabasePeopleBIS extends AbstractComponentType implements IDatabasePeople {

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


    @Port(name = "getCursus",method = "getCursus")
    @Override
    public String getCursus(String id_rfid){

        String sql = "SELECT promo, options, specialite FROM IDatabasePeople where id_rfid =\"" + id_rfid + "\"";

        Log.debug("IDatabasePeople ::: RFID = \""+id_rfid+"\"");

        String promo = null,options = null,specialite = null;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+sql);
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while(rs.next()){
                //Retrieve by column name
                promo  = rs.getString("promo");
                options = rs.getString("options");
                specialite = rs.getString("specialite");

                //Display values
                Log.debug("promo: " + promo);
                Log.debug("options: " + options);
                Log.debug("specialite: " + specialite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JSONArray cursus = new JSONArray();
        try {
            cursus.put(0,promo);
            cursus.put(1, options);
            cursus.put(2, specialite);

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursus.toString();
    }

    @Port(name = "getCursus",method = "getIdPeople")
    @Override
    public String getIdPeople(String id_rfid) {

        String sql;
        String id_people = null;


        String rfid = id_rfid;
        sql = "SELECT id_people FROM IDatabasePeople where id_rfid =\"" + rfid + "\"";

        Log.debug("IDatabasePeople ::: RFID = \""+rfid+"\"");

        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+sql);
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while(rs.next()){
                //Retrieve by column name
                id_people  = rs.getString("id_people");

                //Display values
                Log.debug("id_people: " + id_people);

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id_people;
    }


    @Port(name = "getCursus",method = "getMailPeople")
    @Override
    public String getMailPeople(String id_people) {

        String sql;
        String emailAddress = null;


        sql = "SELECT emailAddress FROM IDatabasePeople where id_people =\"" + id_people + "\"";

        Log.debug("IDatabasePeople ::: id_people = \""+id_people+"\"");

        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+sql);
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



        try {
            while(rs.next()){
                //Retrieve by column name
                emailAddress  = rs.getString("emailAddress");

                //Display values
                Log.debug("emailAddress: " + emailAddress);

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emailAddress;
    }

}

