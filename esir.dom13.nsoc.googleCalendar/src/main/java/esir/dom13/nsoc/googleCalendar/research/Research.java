package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class Research extends AbstractComponentType implements IResearch {
    private String mail;
    private String pw;


    @Override
    public boolean isAuthorized() throws IOException, ServiceException {
        boolean isAutho = false;
        URL feedUrl = null;
        try {
            feedUrl = new URL("https://www.google.com/calendar/feeds/a4thv8s0785u7mo5370c8g39bvnt8jdj@import.calendar.google.com/private/full");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarService service = new CalendarService("NSOC-2013");
        service.setUserCredentials("projet.nsoc2013@gmail.com", "esir2013");
        CalendarEventFeed event = service.getFeed(feedUrl, CalendarEventFeed.class);

        return isAutho;
    }



    @Override
    public boolean isAvailable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
