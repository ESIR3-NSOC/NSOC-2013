package esir.dom13.nsoc.googleCalendar.research;


import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
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
        @ProvidedPort(name = "room_Available", type = PortType.SERVICE, className = IRoomEquipmentResearch.class)
})
/*
@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseBuildings.class)
}) */

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://148.60.11.209/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "user", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "ydr2013.", optional = false),
        @DictionaryAttribute(name = "id_mail", defaultValue = "projet.nsoc2013@gmail.com", optional = false),
        @DictionaryAttribute(name = "password", defaultValue = "esir2013", optional = false)
})


@ComponentType
public class RoomEquipmentResearch extends AbstractComponentType implements IRoomEquipmentResearch{
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


    @Port(name = "room_Available", method = "roomAvailable")
    @Override
    public String roomAvailable(Long _start, Long _end,String equipment){
        Date start = new Date(_start);
        Date end = new Date(_end);
        JSONArray equipments = null;
        try {
            equipments = new JSONArray(equipment);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
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
            equipmentArray = new JSONArray(equipment);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Log.debug("RoomEquipmentResearch ::: Il y a " + equipmentArray.length() + "équipements");

        // Connexion database /
            // JDBC driver name and database URL
            String JDBC_DRIVER, DB_URL;

            //  Database credentials
            String USER, PASS;
            Connection connection = null;
            JDBC_DRIVER = getDictionary().get("JDBC_DRIVER").toString();
            DB_URL = getDictionary().get("DB_URL").toString();
            USER = getDictionary().get("USER").toString();
            PASS = getDictionary().get("PASS").toString();
            //STEP 2: Register JDBC driver
            try {
                Class.forName(JDBC_DRIVER);
                //STEP 3: Open a connection
                Log.debug("Connecting to database...");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                Log.debug("Connecting to database...  OK");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        //Fin connexion Database/




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
        //Execution requete/
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 4: Execute a query
            Log.debug("Creating statement...");
            stmt = connection.createStatement();
            Log.debug("Execution of :: " + request);
            rs = stmt.executeQuery(request);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //Fin execution requete

        //CachedRowSetImpl rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(request);

        try {
            while(rs.next() ){          //&& !isAvailable/
                //Retrieve by column name
                nameRoom  = rs.getString("nameRoom");
                id_Building = rs.getString("id_Building");

                String sqlNomBat = "SELECT nameBuilding FROM IDatabaseBuilding where id_building = \"" + rs.getString("id_Building") + "\"";

                //Execution requete/
                Statement stmt2 = null;
                ResultSet rs2 = null;
                try {
                    //STEP 4: Execute a query
                    Log.debug("Creating statement...");
                    stmt2 = connection.createStatement();
                    Log.debug("Execution of :: " + sqlNomBat);
                    rs2 = stmt2.executeQuery(sqlNomBat);

                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
        //Fin execution requete/
                //CachedRowSetImpl rs2 = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(sqlNomBat);

                while(rs2.next()){
                    nameBuilding = rs2.getString("nameBuilding");
                    System.out.println("nameBuilding: " + nameBuilding);
                }
                rs2.close();
                stmt2.close();

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
                                salleDispo = false;
                             }
                        }
                    }
                    if(salleDispo){
                        if(verificationResa(nameRoom, nameBuilding, start, end)){
                            roomArray = new JSONObject();
                            isAvailable = true;
                            try {
                                roomArray.put("salle",nameRoom);
                                roomArray.put("batiment",nameBuilding);
                                roomArray.put("isAvailable",isAvailable);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            return roomArray.toString();
                        }
                    }
                }else{
                    if(verificationResa(nameRoom, nameBuilding, start, end)){
                        roomArray = new JSONObject();
                        isAvailable = true;
                        try {
                            roomArray.put("salle",nameRoom);
                            roomArray.put("batiment",nameBuilding);
                            roomArray.put("isAvailable",isAvailable);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return roomArray.toString();
                    }
                }
            }

            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();

            try {
                roomArray.put("salle","");
                roomArray.put("batiment","");
                roomArray.put("isAvailable",false);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return roomArray.toString();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return roomArray.toString();

    }         //  */

    private boolean verificationResa(String nameRoom, String nameBuilding, Date min, Date max){
        boolean isAvailable = true;

		/* Connexion au calendrier */
        URL feedUrl = null;
        try {
            feedUrl = new URL("https://www.google.com/calendar/feeds/9u96e3jug29acreg69kc80c00s@group.calendar.google.com/private/full");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        CalendarService myService = new CalendarService("Calendar Reservations");
        try {
            myService.setUserCredentials(getDictionary().get("id_mail").toString(), getDictionary().get("password").toString());
        } catch (AuthenticationException e) {
            e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
        }

		/* Préparation de la requête */
        CalendarQuery myQuery = new CalendarQuery(feedUrl);
        myQuery.setMinimumStartTime(new DateTime(min));
        myQuery.setMaximumStartTime(new DateTime(max));

		/* Réponse */
        CalendarEventFeed myResultsFeed = null;
        try {
            myResultsFeed = myService.query(myQuery,CalendarEventFeed.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (myResultsFeed.getEntries().size() > 0) {
            System.out.println("SIZE CALENDAR RESA : " + myResultsFeed.getEntries().size());

            for(int i=0; i<myResultsFeed.getEntries().size();i++){
                if(isAvailable){
                    CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
                            myResultsFeed.getEntries().get(i);

                    String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
                    if(myEntryWhere.contains(nameRoom) & myEntryWhere.contains(nameBuilding)){
                        isAvailable = false;
                        return isAvailable;
                    }
                }
            }
        }
        return isAvailable;
    }

}