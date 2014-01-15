    package esir.dom13.nsoc.database;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.kevoree.annotation.*;
    import org.kevoree.framework.AbstractComponentType;
    import org.kevoree.framework.MessagePort;
    import org.kevoree.log.Log;

    import java.sql.*;

    /**
     * Created by Clement on 09/01/14.
     */

    @Library(name = "NSOC2013")



    @Requires({
            @RequiredPort(name = "Request", type = PortType.SERVICE, className = IDatabaseConnection.class),
            @RequiredPort(name = "message", type = PortType.MESSAGE)
    })

    @ComponentType
    public class TestDatabase extends AbstractComponentType {


        @Start
        public void start() {
           ResultSet bidule = getPortByName("Request",IDatabaseConnection.class).sendRequestToDatabase("");

            try {
                while(bidule.next()){
                    //Retrieve by column name
                    String promo = bidule.getString("promo");
                    String options = bidule.getString("options");
                    String specialite = bidule.getString("specialite");
                    System.out.println(promo+"  ::  "+options+"  ::  "+specialite);
                    Log.info(promo+"  ::  "+options+"  ::  "+specialite);
                    getPortByName("message",MessagePort.class).process(promo+"  ::  "+options+"  ::  "+specialite);
                }
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        @Stop
        public void stop() {

        }


        @Update
        public void update() {

        }

}
