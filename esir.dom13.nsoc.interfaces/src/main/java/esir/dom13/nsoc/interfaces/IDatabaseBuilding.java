package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Building;
import esir.dom13.nsoc.objects.Room;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 14/11/13
 * Time: 18:00
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabaseBuilding {

    Room getRoomById(int id_room);

    Room getRoomByName(int name_room);

    LinkedList<Room> getRooms(int id_building);

    Building getBuildingById (int id_building);

    Building getBuildingByName(String name_building);

    LinkedList<Building> getBuildings();

    void addBuilding(Building building);

    void addRoom(Building building, Room room);


}
