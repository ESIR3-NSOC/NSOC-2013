package esir.dom13.nsoc.serverWeb;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 05/02/14
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */


import esir.dom13.nsoc.adminDatabaseEquipment.IadminDatabaseEquipment;
import esir.dom13.nsoc.handler.HandlerWebSocket;

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


/**
 * Created by Clement on 20/01/14.
 */


@Library(name = "NSOC2013")

@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
        @RequiredPort(name = "dbEquipement", type =PortType.SERVICE, className = IadminDatabaseEquipment.class),
})
@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "9000", optional = false)
})
@ComponentType
public class ServerWeb_siteAdmin extends AbstractComponentType implements WebSocketHandler {

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
        String equipement = jsonObject.getString("equipement");
        getPortByName("dbEquipement", IadminDatabaseEquipment.class).



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

