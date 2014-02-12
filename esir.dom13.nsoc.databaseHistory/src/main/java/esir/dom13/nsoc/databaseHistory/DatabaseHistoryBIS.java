package esir.dom13.nsoc.databaseHistory;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.*;

/**
 * Created by Clement on 15/01/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "putEntry", type = PortType.SERVICE, className = IDatabaseHistory.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false)
})


@ComponentType
public class DatabaseHistoryBIS extends AbstractComponentType implements IDatabaseHistory {

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

    @Port(name = "putEntry",method = "putEntry")
    @Override
    public void putEntry(String id_people, String teacher, String lesson, String salle, String batiment, String dateHours) {
        String request="INSERT INTO `idatabasehistory`(`name_room`, `name_building`, `id_people`, `date_hours`, `name_lesson`, `name_teacher`) VALUES ('"+salle+"','"+batiment+"','"+id_people+"','"+dateHours+"','"+lesson+"','"+teacher+"')";
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

        Log.debug("Put Enrty FIN");
    }

    @Port(name = "putEntry",method = "getHistory")
    @Override
    public String getHistory(String id_people){

        String value = null;

        JSONArray tableau = new JSONArray();
        String request = "SELECT * FROM `idatabasehistory` WHERE id_people = '" + id_people + "'";
        Log.debug(request);
        Statement stmt = null;

        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: "+request);

            ResultSet rs = stmt.executeQuery(request);

            while(rs.next()){

                JSONArray nombre = new JSONArray();
                value = rs.getString("name_room");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                nombre.put(value);
                value = rs.getString("name_building");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                nombre.put(value);
                value = rs.getString("date_hours");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                nombre.put(value);
                value = rs.getString("name_lesson");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                nombre.put(value);
                value = rs.getString("name_teacher");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                nombre.put(value);

                    tableau.put( nombre);
                      }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}

