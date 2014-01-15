package esir.dom13.nsoc.databaseHistory;

import esir.dom13.nsoc.database.IDatabaseConnection;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.ResultSet;

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
}

