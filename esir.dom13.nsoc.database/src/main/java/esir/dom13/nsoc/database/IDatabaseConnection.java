package esir.dom13.nsoc.database;

import com.sun.rowset.CachedRowSetImpl;


/**
 * Created by Clement on 09/01/14.
 */
public interface IDatabaseConnection {

    CachedRowSetImpl sendRequestToDatabase(String sqlRequest);
    void sendUpdateToDatabase(String sqlRequest);
}
