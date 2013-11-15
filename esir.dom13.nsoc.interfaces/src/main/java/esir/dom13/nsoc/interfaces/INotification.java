package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.People;
import esir.dom13.nsoc.objects.Room;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 15/11/13
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
public interface INotification {

    void successfulBooking(People people, Room room, Date date, int duration);

    void bookingConflict(People people, Room room, Date date, int duration);
}
