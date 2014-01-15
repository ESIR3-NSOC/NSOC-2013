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

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})
@ComponentType
public class DatabaseBuildings extends AbstractComponentType implements IDatabaseBuildings {

    @Start
    public void start() {

    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {

    }

    @Port(name = "getUrlCalendar",method="getUrlCalendar")
    public String getUrlCalendar(String building) {


        String sql;
        String idCalendar = null;


        sql = "SELECT id_building FROM IDatabaseBuilding where nameBuilding =\"" + building + "\"";
        Log.debug("IDatabaseBuilding ::: building = \""+building+"\"");
        Log.debug("IDatabaseBuilding ::: requete sql = \""+sql+"\"");

        CachedRowSetImpl rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(sql);

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

        return idCalendar;
    }


}
