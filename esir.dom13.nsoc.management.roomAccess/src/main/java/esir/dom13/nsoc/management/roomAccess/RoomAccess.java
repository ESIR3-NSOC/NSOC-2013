package esir.dom13.nsoc.management.roomAccess;


import esir.dom13.nsoc.databasePeople.IDatabasePeople;
import esir.dom13.nsoc.googleCalendar.research.IResearch;
import esir.dom13.nsoc.interfaces.IDatabaseADE;
import org.json.JSONArray;
import org.json.JSONException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.sql.SQLException;


/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "getRFID", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "speciality", type = PortType.SERVICE, className = IDatabasePeople.class),
        @RequiredPort(name = "openGache", type = PortType.MESSAGE)
})

@DictionaryType({
        @DictionaryAttribute(name = "Salle", defaultValue = "Salle-001", optional = false),
        @DictionaryAttribute(name = "Batiment", defaultValue = "BAT-7", optional = false),
})

@ComponentType
public class RoomAccess extends AbstractComponentType{

    private String salle,batiment;

    @Start
    public void start() {
        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();
    }

    @Port(name = "getRFID")
    public void getRFID(Object id_rfid){

        String cursus = getPortByName("speciality",IDatabasePeople.class).getCursus(id_rfid.toString());
        Boolean isAuthorized = getPortByName("authorization", IResearch.class).isAuthorized(batiment,salle,cursus);
        if(isAuthorized){
            //TODO Ouvrir gache
            getPortByName("openGache",MessagePort.class).process("authorized");
        }else{
            getPortByName("openGache",MessagePort.class).process("NOTauthorized");
        }
    }
}
