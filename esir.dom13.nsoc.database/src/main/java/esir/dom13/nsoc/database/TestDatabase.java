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

    @Provides({
            @ProvidedPort(name="requete",type = PortType.MESSAGE),
    })


    @Requires({
            @RequiredPort(name = "Request", type = PortType.SERVICE, className = IDatabaseConnection.class),
            @RequiredPort(name = "message", type = PortType.MESSAGE)
    })

    @ComponentType
    public class TestDatabase extends AbstractComponentType {


        @Start
        public void start() {

        }

        @Stop
        public void stop() {

        }


        @Update
        public void update() {

        }

        @Port(name = "requete")
        public void requete(Object object){
            String request = "SELECT promo, options, specialite FROM IDatabasePeople where id_people = \"12002998\"";
            Log.debug(request);
            ResultSet bidule = getPortByName("Request",IDatabaseConnection.class).sendRequestToDatabase(request);
            Log.debug("ResultSet passed");
            try {
                while(bidule.next()){
                    //Retrieve by column name
                    String promo = bidule.getString("promo");
                    String options = bidule.getString("options");
                    String specialite = bidule.getString("specialite");

                    Log.debug(promo + "  ::  " + options + "  ::  " + specialite);
                    getPortByName("message",MessagePort.class).process(promo+"  ::  "+options+"  ::  "+specialite);
                }
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                Log.debug(e.getMessage());
            }
        }

}
