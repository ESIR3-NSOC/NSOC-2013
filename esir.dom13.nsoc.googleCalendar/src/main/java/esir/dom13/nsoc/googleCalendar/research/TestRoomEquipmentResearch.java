    package esir.dom13.nsoc.googleCalendar.research;

    import com.google.gdata.data.DateTime;
    import esir.dom13.nsoc.database.IDatabaseConnection;
    import org.kevoree.annotation.*;
    import org.kevoree.framework.AbstractComponentType;
    import org.kevoree.framework.MessagePort;
    import org.kevoree.log.Log;

    import java.sql.ResultSet;
    import java.sql.SQLException;

    /**
     * Created by Yvan on 20/01/14.
     */

    @Library(name = "NSOC2013")



    @Requires({
            @RequiredPort(name = "Request", type = PortType.SERVICE, className = IRoomEquipmentResearch.class),
            @RequiredPort(name = "message", type = PortType.MESSAGE)
    })

    @ComponentType
    public class TestRoomEquipmentResearch extends AbstractComponentType {


        @Start
        public void start() {
            DateTime startTime = DateTime.parseDateTime("2014-01-20T08:00:00");
            DateTime endTime = DateTime.parseDateTime("2014-01-20T10:00:00");
            String room = getPortByName("Request",RoomEquipmentResearch.class).roomAvailable("ordinateur", startTime, endTime);
            Log.info("TestRoomEquipmentResearch ::: Room  ::  "+room);
        }

        @Stop
        public void stop() {

        }


        @Update
        public void update() {

        }

}
