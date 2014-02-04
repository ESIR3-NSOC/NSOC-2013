package esir.dom13.nsoc.googleCalendar.research;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public interface IRoomEquipmentResearch {

    String roomAvailable(Long start, Long end, String equipments);

}
