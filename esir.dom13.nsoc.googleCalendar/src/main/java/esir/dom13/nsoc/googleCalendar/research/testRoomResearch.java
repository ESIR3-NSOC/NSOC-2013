package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.data.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kevoree.annotation.Library;
import org.kevoree.annotation.PortType;
import org.kevoree.annotation.RequiredPort;
import org.kevoree.annotation.Requires;
import org.kevoree.framework.AbstractComponentType;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 28/01/14
 * Time: 12:30
 * To change this template use File | Settings | File Templates.
 */

@Requires({
        @RequiredPort(name = "testRoomResearch", type = PortType.SERVICE, className = IRoomEquipmentResearch.class)
})

public class testRoomResearch extends AbstractComponentType {

    public void main(String[] args){
        // TODO Auto-generated method stub
        Date startTime = new Date("2014-01-29T08:00:00+01:00");
        Date endTime = new Date("2014-01-29T09:00:00+01:00");
        JSONArray equipment = null;
        try {
            equipment = new JSONArray("[VideoProjecteurs, Ordinateurs]");
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        JSONObject a = null;
        try {
            a = new JSONObject(getPortByName("testRoomResearch", IRoomEquipmentResearch.class).roomAvailable(startTime.toString(), endTime.toString(), equipment.toString()));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        try {
            System.out.println("Salle: " + a.getString("salle"));
            System.out.println("Batiment: " + a.getString("batiment"));
            System.out.println("IsAvailable: " + a.getBoolean("isAvailable"));
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
