package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.People;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 14/11/13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabasePeople {

    People getPeopleById(int id_people);

    People getPeopleByRFID(String id_rfid);

    LinkedList<People> getPeople();

}
