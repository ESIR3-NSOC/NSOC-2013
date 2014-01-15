package esir.dom13.nsoc.databasePeople;

import org.json.JSONException;

import java.sql.SQLException;

/**
 * Created by Clement on 18/12/13.
 */
public interface IDatabasePeople {

    String getCursus(String id_rfid);

    String getIdPeople (String id_rfid);
}
