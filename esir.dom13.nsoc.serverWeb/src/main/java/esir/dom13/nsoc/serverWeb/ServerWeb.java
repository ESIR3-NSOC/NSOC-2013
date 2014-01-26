package esir.dom13.nsoc.serverWeb;

import esir.dom13.nsoc.googleCalendar.research.IResearch;
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


@Library(name = "NSOC")

@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
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

        Log.info("START SOCKET  :)  ");
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
        int duration = jsonObject.getInt("duration");
        JSONArray equipements = jsonObject.getJSONArray("equipment");
        heure.replace("h",":");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date dateBegin = null;
        try {
            dateBegin =  df.parse(date+" "+heure);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeEnd = dateBegin.getTime()+duration*60000;

        Date dateEnd = new Date(timeEnd);
        JSONObject availableRoom =   new JSONObject();
                //new JSONObject(getPortByName("",IResearch.class).findAvailableRoom(dateBegin, dateEnd, equipements));
        //TODO Chercher une salle libre

        // TODO Si salle libre la réserver et envoyé notification Accepté ou non
        if(availableRoom.getBoolean("isAvailable")){
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("salle",availableRoom.getString("salle"));
            jsonResponse.put("batiment",availableRoom.getString("batiment"));
            jsonResponse.put("duration",duration);
            jsonResponse.put("heure",heure);
            jsonResponse.put("date",date);
            jsonResponse.put("isAvailable",true);
            connection.send(jsonResponse.toString());
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
