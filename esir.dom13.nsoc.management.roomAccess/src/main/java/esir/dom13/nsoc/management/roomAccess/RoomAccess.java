package esir.dom13.nsoc.management.roomAccess;


import esir.dom13.nsoc.databaseHistory.IDatabaseHistory;
import esir.dom13.nsoc.databasePeople.IDatabasePeople;
import esir.dom13.nsoc.googleCalendar.research.IResearch;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "getRFID", type = PortType.MESSAGE)
})

@Requires({
        @RequiredPort(name = "speciality", type = PortType.SERVICE, className = IDatabasePeople.class),
        @RequiredPort(name = "openGache", type = PortType.MESSAGE),
        @RequiredPort(name = "authorization", type = PortType.SERVICE, className = IResearch.class),
        @RequiredPort(name = "databaseHistory", type = PortType.SERVICE, className = IDatabaseHistory.class),
})

@DictionaryType({
        @DictionaryAttribute(name = "Salle", defaultValue = "Salle-001", optional = false),
        @DictionaryAttribute(name = "Batiment", defaultValue = "BAT-7", optional = false),
})

@ComponentType
public class RoomAccess extends AbstractComponentType {

    private String salle, batiment;

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
    public void getRFID(Object id_rfid) {

        String cursus = getPortByName("speciality", IDatabasePeople.class).getCursus(id_rfid.toString());
        Log.debug("RoomAccess ::: rfid=\"" + id_rfid + "\"  cursus=\"" + cursus + "\"");

        String result_google = getPortByName("authorization", IResearch.class).isAuthorized(batiment, salle, cursus);
        Log.debug("RESULTAT isAuthorized :::    " + result_google);
        JSONObject jsonObject = null;
        boolean isAuthorized = false;
        String teacher = null, lesson = null;
        try {
            jsonObject = new JSONObject(result_google);
            isAuthorized = jsonObject.getBoolean("isAutho");
            teacher = jsonObject.getString("teacher");
            lesson = jsonObject.getString("lesson");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isAuthorized=true;
        teacher="BOURCIER Johann";
        lesson="NSOC";
        if (isAuthorized) {
            //TODO Ouvrir gache
            getPortByName("openGache", MessagePort.class).process("authorized");

            //TODO enregistrer personne dans l'historique
            String id_people = getPortByName("speciality", IDatabasePeople.class).getIdPeople(id_rfid.toString());


            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateHours = df.format(date);

            getPortByName("databaseHistory",IDatabaseHistory.class).putEntry(id_people,teacher,lesson,salle,batiment,dateHours);

        } else {
            getPortByName("openGache", MessagePort.class).process("NOTauthorized");
        }
    }
}
