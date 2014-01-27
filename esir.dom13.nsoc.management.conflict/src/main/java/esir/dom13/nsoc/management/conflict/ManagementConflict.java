package esir.dom13.nsoc.management.conflict;

import esir.dom13.nsoc.databasePeople.IDatabasePeople;
import esir.dom13.nsoc.googleCalendar.research.IResearch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Clement on 26/01/14.
 */
@Library(name = "NSOC")
@Requires({
        @RequiredPort(name = "SendMail", type = PortType.MESSAGE),
        @RequiredPort(name = "emailAddress", type = PortType.SERVICE, className = IDatabasePeople.class),
        @RequiredPort(name = "conflict", type = PortType.SERVICE, className = IResearch.class)
})


@ComponentType
public class ManagementConflict extends AbstractComponentType {

    private String salle, batiment;

    @Start
    public void start() {
        initialized();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {

        initialized();
    }


    private void initialized() {

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Log.info("ManagementConflict :::  EXECUTE");
                String result = getPortByName("conflict", IResearch.class).ManagementConflict();
                Log.debug("ManagementConflict ::: "+result);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        String id_people = jsonObject.getString("id_people");
                        String batiment = jsonObject.getString("batiment");
                        String salle = jsonObject.getString("salle");
                        String dateBegin = jsonObject.getString("dateBegin");
                        String dateEnd = jsonObject.getString("dateEnd");

                        String emailAddress = getPortByName("emailAddress",IDatabasePeople.class).getMailPeople(id_people);


                        String object="[IMPORTANT] Annulation Réservation Salle";
                        String body="Bonjour,<br><br>Nous avons le regret de vous annoncer que la réservation pour la salle "+salle
                                +" du batiment "+batiment+" entre :<br>"+dateBegin+"<br> et :<br>"+dateEnd+"<br> a du être annulé. Veuillez réserver une nouvelle salle depuis l'interface prévu à cet effet" +
                                "<br><br>Cordialement,<br><br>L'équipe NSOC";
                        JSONObject jsonMail = new JSONObject();
                        jsonMail.put("object",object);
                        jsonMail.put("body",body);
                        jsonMail.put("receiver",emailAddress);
                        getPortByName("SendMail", MessagePort.class).process(jsonMail.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(task,new Date(new Date().getTime()+30000), 30 *1000);


    }
}

