package esir.dom13.nsoc.databasePeople;

import org.json.JSONArray;
import org.json.JSONException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        @DictionaryAttribute(name = "USER", defaultValue = "PASS", optional = false),
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
        USER = getDictionary().get("DB_URL").toString();
        PASS = getDictionary().get("PASS").toString();
    }

    @Stop
    public void stop() {

    }


    @Update
    public void update() {
        JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
        DB_URL = getDictionary().get("DB_URL").toString();
        USER = getDictionary().get("DB_URL").toString();
        PASS = getDictionary().get("PASS").toString();
    }


    @Port(name = "getCursus",method = "getCursus")
    public String getCursus(String id_rfid) {

        Connection conn = null;
        Statement stmt = null;
        String sql;
        String promo = null;
        String options = null;
        String specialite = null;

        String rfid = id_rfid;
        sql = "SELECT promo, options, specialite FROM IDatabasePeople where id_rfid =\"" + rfid + "\"";
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            stmt = conn.createStatement();
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

