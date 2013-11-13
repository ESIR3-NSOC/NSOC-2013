package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.People;
import esir.dom13.nsoc.objects.Room;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public interface IBooking {

    // FR : Réservation d'une salle
    // EN : Booking a room
    boolean bookingRequest(Date date, int duration, Room room, People id_people);


}
