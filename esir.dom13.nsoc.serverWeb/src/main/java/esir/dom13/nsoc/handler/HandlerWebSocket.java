package esir.dom13.nsoc.handler;

import org.kevoree.log.Log;
import org.webbitserver.BaseWebSocketHandler;
import org.webbitserver.WebSocketConnection;

/**
 * Created by Clement on 20/01/14.
 */
public class HandlerWebSocket extends BaseWebSocketHandler{

    private int connexion;

    public void onOpen(WebSocketConnection connection) {
        //connection.send("Other connection :: "+connexion );
        connexion++;
        Log.info("START SOCKET  :  ");
    }

    public void onClose(WebSocketConnection connection) {
        connexion--;
        Log.info("STOP SOCKET  :  ");
    }

    public void onMessage(WebSocketConnection connection, String message){
        Log.debug("Message from peer "+message);
    }
}
