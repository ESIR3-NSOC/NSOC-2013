package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import esir.dom13.nsoc.databaseBuildings.IDatabaseBuildings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.log.Log;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by Clement on 26/01/14.
 */
public class main {

    public static void main(String[] Args) {
        while (true) {

            System.out.println(ManagementConflict());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String ManagementConflict() {
        Log.debug("Beginning isAuthorized");
        JSONArray jsonArray = new JSONArray();
        String urlRoom = "https://www.google.com/calendar/feeds/" + "projet.nsoc2013@gmail.com" + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
            service.setUserCredentials("projet.nsoc2013@gmail.com", "esir2013");
        } catch (AuthenticationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarQuery myQuery = new CalendarQuery(feedUrl);

        DateTime time = new DateTime(new Date().getTime());
        DateTime end = new DateTime(new Date().getTime() + 1296000000);  // +15jours

        Log.debug("GOOGLE_CALENDAR ::: Time start : " + time.toStringRfc822() + "   Time end : " + end.toStringRfc822());
        myQuery.setMinimumStartTime(time);
        myQuery.setMaximumStartTime(end);
        CalendarEventFeed myResultsFeed = null;

        try {
            myResultsFeed = service.query(myQuery, CalendarEventFeed.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (myResultsFeed.getEntries().size() > 0) {
            Log.debug("SIZE : " + myResultsFeed.getEntries().size());
            for (int i = 0; i < myResultsFeed.getEntries().size(); i++) {
                CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                        myResultsFeed.getEntries().get(i);

                String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
                String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                String myEntryContent = firstMatchEntry.getPlainTextContent();
                Date dateBegin = null, dateEnd = null;
                for (When w : firstMatchEntry.getTimes()) {

                    if (w.getStartTime() != null)
                        dateBegin = new Date(w.getStartTime().getValue());
                    if (w.getEndTime() != null)
                        dateEnd = new Date(w.getEndTime().getValue());
                }

                String batiment = myEntryWhere.split("/")[0];
                String salle = myEntryWhere.split("/")[1];
                System.out.println("batiment  ::  " + batiment + "   salle ::  " + salle);
                System.out.println("dateD  :: " + dateBegin.toString() + "   dateE  ::  " + dateEnd.toString());
                boolean result = conflict(batiment, salle, dateBegin, dateEnd);

                if (result == false) {     // Conflit entre notre agenda et ENT
                    // ajout du conflit dans le JSON ARRAY
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("id_people", myEntryTitle);
                        jsonObject.put("batiment", batiment);
                        jsonObject.put("salle", salle);
                        jsonObject.put("dateBegin", dateBegin);
                        jsonObject.put("dateEnd", dateEnd);
                        jsonArray.put(jsonObject);

                        firstMatchEntry.delete();            // Delete entries
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }


        return jsonArray.toString();
    }


    private static boolean conflict(String batiment, String salle, Date dateBegin, Date dateEnd) {

        String urlRoom = "https://www.google.com/calendar/feeds/" + "a4thv8s0785u7mo5370c8g39bvnt8jdj@import.calendar.google.com" + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
            service.setUserCredentials("projet.nsoc2013@gmail.com", "esir2013");
        } catch (AuthenticationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarQuery myQuery = new CalendarQuery(feedUrl);

        DateTime time = new DateTime(dateBegin);
        DateTime end = new DateTime(dateEnd);

        Log.debug("GOOGLE_CALENDAR ::: Time start : " + time.toStringRfc822() + "   Time end : " + end.toStringRfc822());
        myQuery.setMinimumStartTime(time);
        myQuery.setMaximumStartTime(end);
        CalendarEventFeed myResultsFeed = null;

        try {
            myResultsFeed = service.query(myQuery, CalendarEventFeed.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (myResultsFeed.getEntries().size() > 0) {
            Log.debug("SIZE : " + myResultsFeed.getEntries().size());
            for (int i = 0; i < myResultsFeed.getEntries().size(); i++) {
                CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                        myResultsFeed.getEntries().get(i);

                String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
                String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                String myEntryContent = firstMatchEntry.getPlainTextContent();
                Log.debug("\n\nTitle: " + myEntryTitle + "\nWhere: " + myEntryWhere + "\nDescription: " + myEntryContent);
                if (myEntryWhere.contains(salle) && myEntryWhere.contains(batiment)) {
                    Log.debug("GOOGLE_CALENDAR ::: myEntryWhere.contains(salle)");
                    return false;
                }
            }

        }
        return true;
    }
}
