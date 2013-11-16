package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Building;
import esir.dom13.nsoc.objects.Room;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 15/11/13
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public interface IBuildingManagement {

    void addBuilding(Building building);

    void addRoom(Building building, Room room);

}
