package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import org.json.JSONArray;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public interface IResearch {

    String isAuthorized(String batiment, String salle, String cursus);
    public boolean isOccupated(String batiment, String salle);
    String ManagementConflict();

    void bookingRoom(Long start, Long end, String salle, String batiment, String id_people);

}
