package esir.dom13.nsoc.management.roomAccess;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.sql.*;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "getRFID", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "speciality", type = PortType.SERVICE, className = )
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://localhost/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "PASS", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false),
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "Salle", defaultValue = "Salle-001", optional = false),
        @DictionaryAttribute(name = "Batiment", defaultValue = "BAT-7", optional = false),

})
@ComponentType
public class RoomAccess extends AbstractComponentType{

    private String salle,batiment;

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
        salle = getPortByName("Salle").toString();
        batiment = getPortByName("Batiment").toString();
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
        salle = getPortByName("Salle").toString();
        batiment = getPortByName("Batiment").toString();
    }

    @Port(name = "getRFID")
    public void getRFID(Object tagRFID){

        // Get Spécialité, Option, parcours

    }
}
