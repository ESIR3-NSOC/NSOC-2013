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
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false),

})
@ComponentType
public class DatabasePeople extends AbstractComponentType implements IDatabasePeople {

    // JDBC driver name and database URL
    private String JDBC_DRIVER ,DB_URL;

    //  Database credentials
    private String USER,PASS;


    @Start
    public void start() {
        JDBC_DRIVER = getPortByName("JDBC_DRIVER").toString();
        DB_URL = getPortByName("DB_URL").toString();
        USER = getPortByName("DB_URL").toString();
        PASS = getPortByName("PASS").toString();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        JDBC_DRIVER = getPortByName("JDBC_DRIVER").toString();
        DB_URL = getPortByName("DB_URL").toString();
        USER = getPortByName("DB_URL").toString();
        PASS = getPortByName("PASS").toString();
    }

    @Port(name = "getCursus")
    public String getCursus(String id_rfid) throws SQLException, JSONException {

        Connection conn = null;
        Statement stmt = null;
        String sql;
        String promo = null;
        String options = null;
        String specialite = null;

        String rfid = id_rfid;
        sql = "SELECT promo, options, specialite FROM IDatabasePeople where id_rfid =\"" + rfid + "\"";
        ResultSet rs = stmt.executeQuery(sql);
        stmt = conn.createStatement();

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

        rs.close();
        stmt.close();
        conn.close();

        JSONArray cursus = new JSONArray();
        cursus.put(0,promo);
        cursus.put(1, options);
        cursus.put(2, specialite);

        return cursus.toString();
    }
}

