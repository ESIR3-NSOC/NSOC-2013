package esir.dom13.nsoc.serverWeb;

import esir.dom13.nsoc.devices.light.IManagementLight;
import esir.dom13.nsoc.devices.shutter.IManagementShutter;
import esir.dom13.nsoc.handler.HandlerWebSocket;
import esir.dom13.nsoc.scenarii.GetIn_Room.IScenarii;
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
 * Created by Clement on 05/02/14.
 */

@Library(name = "NSOC2013")

@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
        @RequiredPort(name = "CommandLight", type = PortType.SERVICE, className = IManagementLight.class),
        @RequiredPort(name = "CommandShutter", type = PortType.SERVICE, className = IManagementShutter.class),
        @RequiredPort(name = "scnenarii", type = PortType.SERVICE, className = IScenarii.class),
})

@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "9999", optional = false)
})
@ComponentType
public class ServerWeb_GestionRoom extends AbstractComponentType implements WebSocketHandler {

    private WebServer webServer;
    private HandlerWebSocket handlerWebSocket;
    private Peers peers;
    private int port;

    @Start
    public void start() {
        peers = new Peers();
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
        peers.addPeer(connection);
        Log.info("START SOCKET  :  ");
    }

    public void onClose(WebSocketConnection connection) {
        peers.removePeer(connection);
        Log.info("STOP SOCKET  :  ");
    }

    public void onMessage(WebSocketConnection connection, String message) throws JSONException {
        Log.debug("Message from peer " + message);
        //  JSONObject jsonObject = new JSONObject(message);
        JSONObject jsonObject = new JSONObject(message);

        String type = jsonObject.getString("type");

        getPortByName("fakeConsole", MessagePort.class).process(message);
        if (type.equals("lampe")){
            if(jsonObject.getBoolean("value")){
                getPortByName("CommandLight",IManagementLight.class).turnOn();
            } else {
               getPortByName("CommandLight",IManagementLight.class).turnOff();
            }

        } else if (type.equals("volet")){
            if(jsonObject.getBoolean("value")){
              //  getPortByName("CommandShutter",IManagementShutter.class).setDown();
            } else {
              //  getPortByName("CommandShutter",IManagementShutter.class).setUp();
            }
        } else if (type.equals("scenario_entree")){
          //  getPortByName("scenarii",IScenarii.class).scenarii_entree();
        } else if (type.equals("scenario_sortie")){
          //  getPortByName("scenarii",IScenarii.class).scenarii_sortie();
        }

        refresh();

    }

    private void refresh() {
        peers.broadcast("coucou");
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

