package esir.dom13.nsoc.databasePeople;

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
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://localhost/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "root", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false)
})

@ComponentType
public class DatabasePeople extends AbstractComponentType implements IDatabasePeople {

    // JDBC driver name and database URL
    private String JDBC_DRIVER ,DB_URL;

    //  Database credentials
    private String USER,PASS;


    @Start
    public void start() {
        JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
        DB_URL = getDictionary().get("DB_URL").toString();
        USER = getDictionary().get("USER").toString();
        PASS = getDictionary().get("PASS").toString();
    }

    @Stop
    public void stop() {

    }


    @Update
    public void update() {
        JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
        DB_URL = getDictionary().get("DB_URL").toString();
        USER = getDictionary().get("USER").toString();
        PASS = getDictionary().get("PASS").toString();
    }


    @Port(name = "getCursus",method = "getCursus")
    public String getCursus(String id_rfid){

        Connection conn = null;
        Statement stmt = null;
        String sql;
        String promo = null;
        String options = null;
        String specialite = null;

        String rfid = id_rfid;
        sql = "SELECT promo, options, specialite FROM IDatabasePeople where id_rfid =\"" + rfid + "\"";
        ResultSet rs = null;
        Log.debug("IDatabasePeople ::: RFID = \""+rfid+"\"");
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            Log.debug("Connecting to database ::: IDatabasePeople");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        JSONArray cursus = new JSONArray();
        try {
            cursus.put(0,promo);
            cursus.put(1, options);
            cursus.put(2, specialite);

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return cursus.toString();
    }
}

