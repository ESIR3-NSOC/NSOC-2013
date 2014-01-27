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
    public JSONObject roomAvailable(Date start, Date end,JSONArray equipments){
        Log.debug("Beginning roomAvailable");
        String nameRoom = null;
        String id_Building = null;
        String request = null;

        String nameRoomAvailable = null;
        String nameBuildingAvailable = null;
        Boolean isAvailable = false;
        String nameBuilding = null;


        JSONArray equipmentArray = null;
        JSONObject roomArray = new JSONObject();

        try {
            equipmentArray = new JSONArray(equipments);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Log.debug("RoomEquipmentResearch ::: Il y a " + equipmentArray.length() + "équipements");



        if(equipmentArray.length()==0){
            request = "SELECT nameRoom, id_Building FROM IDatabaseRoom where nameEquipment = \"\"";
            Log.debug("RoomEquipmentResearch ::: requete sql = \""+request+"\"");
        }else{

            String equipementsRequete = "";
            for(int i=0;i<equipmentArray.length();i++){
                if(i<equipmentArray.length()-1){
                    try {
                        equipementsRequete = equipementsRequete + "\"" + equipmentArray.getString(i) + "\",";
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else{
                    try {
                        equipementsRequete = equipementsRequete + "\"" + equipmentArray.getString(i) + "\"";
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }


            request = "SELECT nameRoom, id_Building FROM IDatabaseRoom where nameEquipment IN (" + equipementsRequete +") GROUP BY nameRoom, id_Building HAVING COUNT(DISTINCT nameEquipment) = " + equipmentArray.length() + ";";
            Log.debug("RoomEquipmentResearch ::: requete sql = \""+request+"\"");
        }
        CachedRowSetImpl rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(request);

        try {
            while(rs.next() /*&& !isAvailable*/){
                //Retrieve by column name
                nameRoom  = rs.getString("nameRoom");
                id_Building = rs.getString("id_Building");

                String sqlNomBat = "SELECT nameBuilding FROM IDatabaseBuilding where id_building = \"" + rs.getString("id_Building") + "\"";
                CachedRowSetImpl rs2 = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(sqlNomBat);

                while(rs2.next()){
                    nameBuilding = rs2.getString("nameBuilding");
                    System.out.println("nameBuilding: " + nameBuilding);
                }
                rs2.close();

                String urlRoom = "https://www.google.com/calendar/feeds/" + id_Building + "/private/full";
                URL feedUrl = null;
                CalendarService service = new CalendarService("NSOC-2013");
                try {
                    feedUrl = new URL(urlRoom);
                } catch (MalformedURLException e) {
                    e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                }

                try {
                    service.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());


                } catch (AuthenticationException e) {
                    e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                }
                CalendarQuery myQuery = new CalendarQuery(feedUrl);

                Log.debug("GOOGLE_CALENDAR ::: Time start : " + start + " Time end : " + end);
                myQuery.setMinimumStartTime(new DateTime(start));
                myQuery.setMaximumStartTime(new DateTime(end));
                CalendarEventFeed myResultsFeed = null;

                try {
                    myResultsFeed = service.query(myQuery, CalendarEventFeed.class);
                } catch (IOException e) {
                    e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                } catch (ServiceException e) {
                    e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
                }


                boolean salleDispo = true;
                if (myResultsFeed.getEntries().size() > 0) {
                    System.out.println("SIZE : " + myResultsFeed.getEntries().size());

                    for(int i=0; i<myResultsFeed.getEntries().size();i++){
                        if(salleDispo){
                            CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                                    myResultsFeed.getEntries().get(i);

                            String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
                            String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                            String myEntryContent = firstMatchEntry.getPlainTextContent();
                            //System.out.println("\n\nTitle: " + myEntryTitle + "\nWhere: " + myEntryWhere + "\nDescription: " + myEntryContent);
                            if(myEntryWhere.contains(nameRoom)){
                                //System.out.println("RoomEquipmentResearch ::: Room " + nameRoom + "NOT available from " + min.toString() + " to " + max.toString());
                                salleDispo = false;
                                System.out.println("FAUX");
                            }else{
                                //System.out.println("RoomEquipmentResearch ::: Room " + nameRoom + "available from " + min.toString() + " to " + max.toString());
                            }
                        }
                    }
                    if(salleDispo){
                        System.out.println("YES");

                        try {
                            roomArray.put("salle",nameRoom);
                            roomArray.put("batiment",nameBuilding);
                            roomArray.put("isAvailable",isAvailable);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return roomArray;
                    }
                }else{

                    try {
                        roomArray.put("salle",nameRoom);
                        roomArray.put("batiment",nameBuilding);
                        roomArray.put("isAvailable",isAvailable);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    return roomArray;
                }
            }

            //STEP 6: Clean-up environment
            rs.close();



            try {
                roomArray.put("salle","");
                roomArray.put("batiment","");
                roomArray.put("isAvailable",false);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return roomArray;
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return roomArray;

    }
}