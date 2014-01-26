package esir.dom13.nsoc.googleCalendar.research;


import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import esir.dom13.nsoc.database.IDatabaseConnection;
import esir.dom13.nsoc.databaseBuildings.IDatabaseBuildings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;
import com.sun.rowset.CachedRowSetImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 20/01/14
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")
@Provides({
        @ProvidedPort(name = "roomAvailable", type = PortType.SERVICE, className = IRoomEquipmentResearch.class)
})
@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "id_mail", defaultValue = "projet.nsoc2013@gmail.com", optional = false),
        @DictionaryAttribute(name = "password", defaultValue = "esir2013", optional = false)
})
@ComponentType
public class RoomEquipmentResearch extends AbstractComponentType implements IRoomEquipmentResearch {
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

    @Port(name = "roomAvailable", method = "roomAvailable")
    @Override
    public String roomAvailable(String equipment, DateTime start, DateTime end){
        Log.debug("Beginning roomAvailable");
        String roomAvailable = null;
        String nameRoom = null;
        String id_Building = null;

        String request= "SELECT nameRoom, id_Building FROM IDatabaseRoom where nameEquipment =\"" + equipment + "\"";;
        Log.debug("RoomEquipmentResearch ::: requete sql = \""+request+"\"");
        CachedRowSetImpl rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(request);

        try {
            while(rs.next()){
                //Retrieve by column name
                nameRoom  = rs.getString("nameRoom");
                id_Building  = rs.getString("id_Building");

                //Display values
                Log.debug("Room name: " + nameRoom);
                Log.debug("Building url: " + id_Building);

                String urlRoom = "https://www.google.com/calendar/feeds/" + id_Building + "/private/full";
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

                Log.debug("GOOGLE_CALENDAR ::: Time start : " + start + "   Time end : " + end);
                myQuery.setMinimumStartTime(start);
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
                    System.out.println("SIZE : " + myResultsFeed.getEntries().size());
                    for(int i=0; i<myResultsFeed.getEntries().size();i++){
                        CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                                myResultsFeed.getEntries().get(i);

                        String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
                        String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                        String myEntryContent = firstMatchEntry.getPlainTextContent();
                        Log.debug("RoomEquipmentResearch ::: Title: " + myEntryTitle + " Where: " + myEntryWhere + " Description: " + myEntryContent);
                        if(myEntryWhere.contains(nameRoom)){
                            Log.debug("RoomEquipmentResearch ::: Room " + nameRoom + "NOT available from " + start.toString() + " to " + end.toString());
                        }else{
                            Log.debug("RoomEquipmentResearch ::: Room " + nameRoom + "available from " + start.toString() + " to " + end.toString());
                            roomAvailable = nameRoom;
                            return roomAvailable;
                        }
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}