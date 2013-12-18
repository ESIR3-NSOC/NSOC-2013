package esir.dom13.nsoc.databaseBuildings;

import kotlin.properties.delegation.NotNullVar;
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
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 18/12/13
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")


@Provides({
        @ProvidedPort(name = "getCursus", type = PortType.SERVICE, className = IIdCalendar.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://localhost/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "PASS", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false),

})
@ComponentType
public class IdCalendar extends AbstractComponentType implements IIdCalendar{
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

    @Port(name = "getUrlCalendar")
    public String getUrlCalendar(String building) {

        Connection conn = null;
        Statement stmt = null;
        String sql;
        String idCalendar = null;


        sql = "SELECT id_building, FROM IDatabaseBuilding where nameBuilding =\"" + building + "\"";
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
                idCalendar  = rs.getString("id_building");

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
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return idCalendar;
    }


}
