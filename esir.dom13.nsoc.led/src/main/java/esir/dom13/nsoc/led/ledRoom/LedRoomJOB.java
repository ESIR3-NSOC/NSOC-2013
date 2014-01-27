package esir.dom13.nsoc.led.ledRoom;


import esir.dom13.nsoc.googleCalendar.research.IResearch;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 08/01/14
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
@Requires({
        @RequiredPort(name = "Occupation", type = PortType.SERVICE, className = IResearch.class),
        @RequiredPort(name = "lightOccupation", type = PortType.MESSAGE),
})
@DictionaryType({
        @DictionaryAttribute(name = "Salle", defaultValue = "001", optional = false),
        @DictionaryAttribute(name = "Batiment", defaultValue = "B41", optional = false),
})
@ComponentType
public class LedRoomJOB extends AbstractComponentType{

    private String salle,batiment;
    @Start
    public void start() {

        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();
               initialized();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {

        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();

        initialized();
    }




    private void initialized(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Log.info("LedRoomJOB :::  EXECUTE");
                boolean light = getPortByName("Occupation", IResearch.class).isOccupated(batiment, salle);
                getPortByName("lightOccupation", MessagePort.class).process(light);
                Log.info("LedRoomJOB :::  etat light : " + light);
            }
        };
        timer.scheduleAtFixedRate(task, new Date(new Date().getTime()+30000), 60* 1000);
    }
}


