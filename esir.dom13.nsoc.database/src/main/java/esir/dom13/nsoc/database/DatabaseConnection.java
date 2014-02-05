package esir.dom13.nsoc.database;

import com.sun.rowset.CachedRowSetImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.*;

/**
 * Created by Clement on 09/01/14.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "Request", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false)
})

@ComponentType
public class DatabaseConnection extends AbstractComponentType implements IDatabaseConnection {

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


    @Port(name = "Request", method = "sendRequestToDatabase")
    @Override
    public CachedRowSetImpl sendRequestToDatabase(String sqlRequest) {


        Statement stmt = null;
        ResultSet rs = null;
        CachedRowSetImpl cachedRowSet = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+sqlRequest);
            rs = stmt.executeQuery(sqlRequest);

            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(rs);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cachedRowSet;


    }

    @Port(name = "Request", method = "sendUpdateToDatabase")
    @Override
    public void sendUpdateToDatabase(String sqlRequest) {


        Statement stmt = null;
        CachedRowSetImpl cachedRowSet = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+sqlRequest);

            stmt.executeUpdate(sqlRequest);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
