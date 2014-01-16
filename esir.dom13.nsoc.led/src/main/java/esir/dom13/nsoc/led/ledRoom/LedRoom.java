package esir.dom13.nsoc.led.ledRoom;


import esir.dom13.nsoc.googleCalendar.research.IResearch;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 08/01/14
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
@Requires({
        @RequiredPort(name = "Occupation", type = PortType.SERVICE, className = IResearch.class)
})

@Provides({
        @ProvidedPort(name = "lightOccupation", type = PortType.MESSAGE),
})
@DictionaryType({
        @DictionaryAttribute(name = "Salle", defaultValue = "001", optional = false),
        @DictionaryAttribute(name = "Batiment", defaultValue = "B7", optional = false),
        @DictionaryAttribute(name = "Heure_de_debut", defaultValue = "8", optional = false),
        @DictionaryAttribute(name = "Heure_de_fin", defaultValue = "18", optional = false),
        @DictionaryAttribute(name = "frequence_en_minute", defaultValue = "15", optional = false),
})
@ComponentType
public class LedRoom extends AbstractComponentType implements Job{

    private String salle,batiment;
    private String hourStart, hourEnd, frequency;
    private Scheduler scheduler;
    @Start
    public void start() {

        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();
        hourStart = getDictionary().get("Heure_de_debut").toString();
        hourEnd = getDictionary().get("Heure_de_fin").toString();
        frequency = getDictionary().get("frequence_en_minute") .toString();

        initialized();
    }

    @Stop
    public void stop() {
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Update
    public void update() {

        salle = getDictionary().get("Salle").toString();
        batiment = getDictionary().get("Batiment").toString();
        hourStart = getDictionary().get("Heure_de_debut").toString();
        hourEnd = getDictionary().get("Heure_de_fin").toString();
        frequency = getDictionary().get("frequence_en_minute") .toString();
        initialized();
    }



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
           boolean light = getPortByName("Occupation", IResearch.class).isOccupated(batiment, salle);
        getPortByName("lightOccupation", MessagePort.class).process(light);
    }

    private void initialized(){
        JobDetail job = JobBuilder.newJob(LedRoom.class)
                .withIdentity("trigger3", "group1").build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("5 0/" + frequency + " " + hourStart + "-" + hourEnd + " ? * MON-FRI"))
                .build();


        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
