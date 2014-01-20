package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.data.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public interface IRoomEquipmentResearch {

    String roomAvailable(String equipment, DateTime start, DateTime end);
}
