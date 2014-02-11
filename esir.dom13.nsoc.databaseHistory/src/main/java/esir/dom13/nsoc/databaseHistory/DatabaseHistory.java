package esir.dom13.nsoc.databaseHistory;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Clement on 15/01/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "putEntry", type = PortType.SERVICE, className = IDatabaseHistory.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class DatabaseHistory extends AbstractComponentType implements IDatabaseHistory {


    @Start
    public void start() {
    }

    @Stop
    public void stop() {

    }


    @Update
    public void update() {
    }


    @Port(name = "putEntry",method = "putEntry")
    @Override
    public void putEntry(String id_people, String teacher, String lesson, String salle, String batiment, String dateHours) {
        String request="INSERT INTO `idatabasehistory`(`name_room`, `name_building`, `id_people`, `date_hours`, `name_lesson`, `name_teacher`) VALUES ('"+salle+"','"+batiment+"','"+id_people+"','"+dateHours+"','"+lesson+"','"+teacher+"')";
        Log.debug(request);
        getPortByName("connectDatabase",IDatabaseConnection.class).sendUpdateToDatabase(request);
        Log.debug("Put Enrty FIN");
    }

    @Port(name = "putEntry",method = "getHistory")
    @Override
    public String getHistory(String id_people){

        String value = null;
        JSONArray nombre = new JSONArray();
        String sql = "SELECT * FROM `idatabasehistory` WHERE id_people = '" + id_people + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);


        try {
            while(rs.next()){


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
                nombre.put("/");

            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return nombre.toString();
    }
}

