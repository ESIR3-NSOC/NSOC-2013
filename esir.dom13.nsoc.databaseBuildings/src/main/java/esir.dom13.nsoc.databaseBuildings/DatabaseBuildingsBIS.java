package esir.dom13.nsoc.databaseBuildings;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;


/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 18/12/13
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")


@Provides({
        @ProvidedPort(name = "getUrlCalendar", type = PortType.SERVICE, className = IDatabaseBuildings.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false)
})

@ComponentType
public class DatabaseBuildingsBIS extends AbstractComponentType implements IDatabaseBuildings {
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

    @Port(name = "getUrlCalendar", method = "getUrlCalendar")
    public String getUrlCalendar(String building) {


        String sql;
        String idCalendar = null;


        sql = "SELECT id_building FROM IDatabaseBuilding where nameBuilding =\"" + building + "\"";
        Log.debug("IDatabaseBuilding ::: building = \"" + building + "\"");
        Log.debug("IDatabaseBuilding ::: requete sql = \"" + sql + "\"");
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: " + sql);
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            while (rs.next()) {
                //Retrieve by column name
                idCalendar = rs.getString("id_building");

                //Display values
                Log.debug("Building: " + building);
                Log.debug("Calendar url: " + idCalendar);
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
        return idCalendar;
    }


}
