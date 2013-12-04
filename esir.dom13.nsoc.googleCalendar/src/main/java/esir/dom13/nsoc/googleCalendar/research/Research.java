package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
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
    private DateTime start;
    private DateTime end;

    @Override
    public boolean isAuthorized() throws ServiceException, IOException {
        boolean isAutho = false;
        URL feedUrl = null;
        try {
            feedUrl = new URL("https://www.google.com/calendar/feeds/a4thv8s0785u7mo5370c8g39bvnt8jdj@import.calendar.google.com/private/full");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarService myService = new CalendarService("Calendar ADE");
        myService.setUserCredentials(mail, pw);


        CalendarQuery myQuery = new CalendarQuery(feedUrl);
        myQuery.setMinimumStartTime(start);
        myQuery.setMaximumStartTime(end);

        // Send the request and receive the response:
        CalendarEventFeed myResultsFeed = myService.query(myQuery,CalendarEventFeed.class);
        if (myResultsFeed.getEntries().size() > 0) {
            System.out.println("SIZE : " + myResultsFeed.getEntries().size());
            for(int i=0; i<myResultsFeed.getEntries().size();i++){
                CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                        myResultsFeed.getEntries().get(i);

                String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
                String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                String myEntryContent = firstMatchEntry.getPlainTextContent();
                System.out.println("Title: " + myEntryTitle + "\nWhere: " + myEntryWhere + "\nDescription: " + myEntryContent);
				/*if(myEntryContent.contains(speciality)){
					System.out.println("AUTHORISED");
					return true;
				}else{
					System.out.println("NOT AUTHORISED");
					return false;
				}*/
            }
        }else{
            System.out.println("NOT AUTHORISED");
            return false;
        }
        return isAutho;
    }


    @Override
    public boolean isAvailable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
