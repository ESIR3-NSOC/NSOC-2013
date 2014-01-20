package esir.dom13.nsoc.led.ledRoom;

import org.kevoree.log.Log;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Clement on 19/01/14.
 */
public class LedRoomJOB implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Log.debug("TestJob run successfully...");
    }
}
