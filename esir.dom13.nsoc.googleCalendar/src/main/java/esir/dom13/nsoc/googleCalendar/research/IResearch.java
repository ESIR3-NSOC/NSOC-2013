package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 04/12/13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public interface IResearch {

    String isAuthorized(String batiment, String salle, String cursus);

}
