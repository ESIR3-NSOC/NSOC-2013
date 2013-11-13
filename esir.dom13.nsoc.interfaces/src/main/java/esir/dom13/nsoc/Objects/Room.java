package esir.dom13.nsoc.objects;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 12/11/13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public class Room {


    private   int roomId;
    private   int buildingId;
    private   String roomName;
    private   LinkedList<Equipment> listEquipment;


    public Room(int room,String name, int building, LinkedList<Equipment> list){

        roomId = room;
        buildingId = building;
        listEquipment = list;

    }

    public int getRoomId() {
        return roomId;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public String getRoomName() {
       return roomName;
    }

    public LinkedList<Equipment> getListEquipment() {
        return listEquipment;
    }


}
