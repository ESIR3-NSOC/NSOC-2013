package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.data.DateTime;
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

    JSONObject roomAvailable(Date start, Date end, JSONArray equipments);
}
