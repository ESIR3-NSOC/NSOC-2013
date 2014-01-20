package esir.dom13.nsoc.serverWeb;

import esir.dom13.nsoc.handler.HandlerWebSocket;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;
import org.webbitserver.WebServer;
import org.webbitserver.WebServers;
import org.webbitserver.handler.StaticFileHandler;

/**
 * Created by Clement on 20/01/14.
 */


@Library(name = "NSOC")
@DictionaryType({
        @DictionaryAttribute(name = "port", defaultValue = "8080", optional = false)
})
@ComponentType
public class ServerWeb extends AbstractComponentType {

    private WebServer webServer;
     private HandlerWebSocket handlerWebSocket;

    private int port;

    @Start
    public void start() {

        handlerWebSocket = new HandlerWebSocket();
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
        webServer.add("/socket",handlerWebSocket )
                 .add(new StaticFileHandler("/web"));
    }
}
