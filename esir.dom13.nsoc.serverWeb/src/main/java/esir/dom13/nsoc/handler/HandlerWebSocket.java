package esir.dom13.nsoc.handler;

import org.json.JSONException;
import org.kevoree.annotation.ComponentType;
import org.kevoree.annotation.PortType;
import org.kevoree.annotation.RequiredPort;
import org.kevoree.annotation.Requires;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.kevoree.log.Log;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

/**
 * Created by Clement on 20/01/14.
 */


@Requires({
        @RequiredPort(name = "fakeConsole", type = PortType.MESSAGE),
})
@ComponentType
public class HandlerWebSocket extends AbstractComponentType implements WebSocketHandler {

    private int connexion;

    public void onOpen(WebSocketConnection connection) {
        //connection.send("Other connection :: "+connexion );
        connexion++;
        Log.info("START SOCKET  :)  ");
    }

    public void onClose(WebSocketConnection connection) {
        connexion--;
        Log.info("STOP SOCKET  :  ");
    }

    public void onMessage(WebSocketConnection connection, String message) throws JSONException {
        Log.debug("Message from peer " + message);
      //  JSONObject jsonObject = new JSONObject(message);

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
