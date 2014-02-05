package esir.dom13.nsoc.serverWeb;

/**
 * Created by Clement on 05/02/14.
 */
import org.webbitserver.WebSocketConnection;

import java.util.ArrayList;
import java.util.List;


public class Peers {

    private List<WebSocketConnection> connections;


    public Peers( ){
        this.connections = new ArrayList<WebSocketConnection>();

    }

    public void addPeer(WebSocketConnection webSocketConnection){
        connections.add(webSocketConnection);
    }

    public void removePeer(WebSocketConnection webSocketConnection){
        connections.remove(webSocketConnection);
    }


    public void broadcast(String msg)
    {
        for (WebSocketConnection connection : connections)
        {
            connection.send(msg);
        }
    }



}
