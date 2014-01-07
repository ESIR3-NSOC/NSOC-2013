package esir.dom13.nsoc.databaseBuildings;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

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
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false)

})
@ComponentType
public class DatabaseBuildings extends AbstractComponentType implements IDatabaseBuildings {
    // JDBC driver name and database URL
    private String JDBC_DRIVER ,DB_URL;

    //  Database credentials
    private String  USER,PASS;


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

    @Port(name = "getUrlCalendar",method="getUrlCalendar")
    public String getUrlCalendar(String building) {

        Connection conn = null;
        Statement stmt = null;
        String sql;
        String idCalendar = null;


        sql = "SELECT id_building FROM IDatabaseBuilding where nameBuilding =\"" + building + "\"";
        ResultSet rs = null;
        Log.debug("IDatabaseBuilding ::: building = \""+building+"\"");
        Log.debug("IDatabaseBuilding ::: requete sql = \""+sql+"\"");
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            //STEP 3: Open a connection
            Log.debug("Connecting to database ::: IDatabaseBuilding");
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
