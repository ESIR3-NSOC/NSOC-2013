package esir.dom13.nsoc.serverWeb;


import esir.dom13.nsoc.googleCalendar.research.IResearch;
import esir.dom13.nsoc.googleCalendar.research.IRoomEquipmentResearch;
import esir.dom13.nsoc.handler.HandlerWebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;
import org.webbitserver.handler.StaticFileHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Clement on 20/01/14.
 */


@Library(name = "NSOC2013")

@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
        @RequiredPort(name = "roomAvailable", type = PortType.SERVICE, className = IRoomEquipmentResearch.class),
        @RequiredPort(name = "bookingRoom", type = PortType.SERVICE,className = IResearch.class),
})
@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "8080", optional = false)
})
@ComponentType
public class ServerWeb extends AbstractComponentType implements WebSocketHandler {

    private WebServer webServer;
    private HandlerWebSocket handlerWebSocket;

    private int port;

    @Start
    public void start() {

        createWebServer();
        webServer.start();
        Log.info("Server running at " + webServer.getUri());

    }

    @Stop
    public void stop() {
        webServer.stop();
    }


    @Update
    public void update() {
        webServer.stop();
        createWebServer();
        webServer.start();
        Log.info("UPDATE :: Server running at " + webServer.getUri());
    }


    private void createWebServer() {
        port = Integer.parseInt(getDictionary().get("port").toString());
        webServer = WebServers.createWebServer(port);
        webServer.add("/socket", this)
                .add(new StaticFileHandler("/web"));
    }

    public void onOpen(WebSocketConnection connection) {
        //connection.send("Other connection :: "+connexion );

        Log.info("START SOCKET  :  ");
    }

    public void onClose(WebSocketConnection connection) {

        Log.info("STOP SOCKET  :  ");
    }

    public void onMessage(WebSocketConnection connection, String message) throws JSONException {
        Log.debug("Message from peer " + message);
        //  JSONObject jsonObject = new JSONObject(message);
        JSONObject jsonObject = new JSONObject(message);
        String id_people = jsonObject.getString("id_people");
        String date = jsonObject.getString("date");
        String heure = jsonObject.getString("time");
        int duration = Integer.parseInt(jsonObject.getString("duration"));
        JSONArray equipements = jsonObject.getJSONArray("equipment");
        heure = heure.replace("h",":");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dateBegin = null;
        Date dateEnd =null;
        try {
            dateBegin =  df.parse(date+" "+heure);
            long timeEnd = dateBegin.getTime()+duration*60000;
            dateEnd = new Date(timeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.info("----------------------------------------------"+dateBegin.toString());

        String roomAvailable = getPortByName("roomAvailable",IRoomEquipmentResearch.class).roomAvailable(dateBegin.getTime(), dateEnd.getTime(), equipements.toString());
        JSONObject availableRoom =   new JSONObject(roomAvailable);

        //TODO Chercher une salle libre

        // TODO Si salle libre la réserver et envoyé notification Accepté ou non
        if(availableRoom.getBoolean("isAvailable")){
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("salle",availableRoom.getString("salle"));
            jsonResponse.put("batiment",availableRoom.getString("batiment"));
            jsonResponse.put("start",dateBegin.toString());
            jsonResponse.put("end",dateEnd.toString());
            jsonResponse.put("id_people",id_people);
            jsonResponse.put("isAvailable",true);
            connection.send(jsonResponse.toString());

            //TODO creer salle
            getPortByName("bookingRoom", IResearch.class).bookingRoom(dateBegin.getTime(),dateEnd.getTime(),availableRoom.getString("salle"),availableRoom.getString("batiment"),id_people);
        } else{
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("isAvailable",false);
            connection.send(jsonResponse.toString());
        }
        getPortByName("fakeConsole", MessagePort.class).process(message);
    }

    @Override
    public void onMessage(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.debug("Message from peer " + msg);

    }

    @Override
    public void onPing(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.info("START SOCKET  :  ");
    }

    @Override
    public void onPong(WebSocketConnection connection, byte[] msg) throws Throwable {
        Log.info("STOP SOCKET  :  ");
    }
}
