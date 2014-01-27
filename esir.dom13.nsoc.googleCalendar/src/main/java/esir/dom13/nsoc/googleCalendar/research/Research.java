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
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")
@Provides({
        @ProvidedPort(name = "Authorization", type = PortType.SERVICE, className = IResearch.class),
})
@Requires({

        @RequiredPort(name = "id_Room", type = PortType.SERVICE, className = IDatabaseBuildings.class)

})
@DictionaryType({
        @DictionaryAttribute(name = "id_mail", defaultValue = "projet.nsoc2013@gmail.com", optional = false),
        @DictionaryAttribute(name = "password", defaultValue = "esir2013", optional = false)
})
@ComponentType
public class Research extends AbstractComponentType implements IResearch {
    private String mail;
    private String pw;

    @Start
    public void start() {
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {

    }

    @Port(name = "Authorization", method = "isAuthorized")
    @Override
    public String isAuthorized(String batiment, String salle, String cursus) {
        Log.debug("Beginning isAuthorized");
        JSONArray cursusArray = null;
        boolean isAutho = false;
        try {
            cursusArray = new JSONArray(cursus);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String promo = null;
        String speciality = null;
        String option = null;
        try {
            promo = cursusArray.getString(0);
            speciality = cursusArray.getString(1);
            option = cursusArray.getString(2);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        String urlRoom = "https://www.google.com/calendar/feeds/" + getPortByName("id_Room", IDatabaseBuildings.class).getUrlCalendar(batiment) + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            service.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());


        } catch (AuthenticationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarQuery myQuery = new CalendarQuery(feedUrl);

        DateTime time = new DateTime(new Date().getTime());
        DateTime end = new DateTime(time.getValue() + 600000);

        Log.debug("GOOGLE_CALENDAR ::: Time start : " + time.toStringRfc822() + "   Time end : " + end.toStringRfc822());
        myQuery.setMinimumStartTime(time);
        myQuery.setMaximumStartTime(end);
        CalendarEventFeed myResultsFeed = null;
        String teacher = "";
        String lesson = "";
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
                    if (myEntryContent.contains(promo)) {
                        Log.debug("GOOGLE_CALENDAR ::: myEntryContent.contains(promo)");
                        if (myEntryContent.contains(speciality)) {
                            Log.debug("GOOGLE_CALENDAR ::: myEntryContent.contains(speciality)");
                            Log.debug("AUTHORISED");
                            String[] content = myEntryContent.split("\n");
                            teacher = content[content.length - 2];
                            lesson = myEntryTitle;
                            isAutho = true;


                        } else {
                            if (myEntryContent.contains(option)) {
                                Log.debug("GOOGLE_CALENDAR ::: myEntryContent.contains(option)");
                                Log.debug("AUTHORISED");
                                String[] content = myEntryContent.split("\n");
                                teacher = content[content.length - 2];
                                lesson = myEntryTitle;
                                isAutho = true;

                            }
                        }
                    } else {
                        System.out.println("NOT AUTHORISED");
                        isAutho = false;
                    }
                }
            }

        } else {
            isAutho = false;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isAutho", isAutho);
            jsonObject.put("teacher", teacher);
            jsonObject.put("lesson", lesson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Port(name = "Authorization", method = "isOccupated")
    @Override
    public boolean isOccupated(String batiment, String salle) {
        boolean isOccup = false;

        String urlRoom = "https://www.google.com/calendar/feeds/" + getPortByName("id_Room", IDatabaseBuildings.class).getUrlCalendar(batiment) + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            service.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());
        } catch (AuthenticationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarQuery myQuery = new CalendarQuery(feedUrl);

        DateTime time = new DateTime(new Date().getTime());
        DateTime end = new DateTime(time.getValue() + 600000);

        Log.debug("GOOGLE_CALENDAR ::: Time start : " + time.toStringRfc822() + "   Time end : " + end.toUiString() + "\n" + end.toString() + "     " + end.toStringRfc822());
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
                String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                if (myEntryWhere.contains(salle)) {
                    isOccup = true;
                }
            }
        }


        return isOccup;
    }

    @Port(name = "Authorization", method = "ManagementConflict")
    @Override
    public String ManagementConflict() {
        Log.debug("Beginning isAuthorized");
        JSONArray jsonArray = new JSONArray();
        String urlRoom = "https://www.google.com/calendar/feeds/" + "projet.nsoc2013@gmail.com" + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
            service.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());
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
                Log.debug(myEntryTitle+"   "+myEntryWhere+"  "+myEntryContent);
                String batiment = myEntryWhere.split("/")[0];
                String salle = myEntryWhere.split("/")[1];

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

                        firstMatchEntry.delete(); // Delete entry
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ServiceException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }


        return jsonArray.toString();
    }


    private boolean conflict(String batiment, String salle, Date dateBegin, Date dateEnd) {

        String urlRoom = "https://www.google.com/calendar/feeds/" + getPortByName("id_Room", IDatabaseBuildings.class).getUrlCalendar(batiment) + "/private/full";
        URL feedUrl = null;
        CalendarService service = new CalendarService("NSOC-2013");
        try {
            feedUrl = new URL(urlRoom);
            service.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());
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

   //TODO Gestion authorization avec notre calendar
   //TODO gestion occupation avec notre calendar
}