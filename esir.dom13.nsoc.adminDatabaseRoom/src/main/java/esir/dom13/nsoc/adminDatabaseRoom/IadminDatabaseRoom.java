package esir.dom13.nsoc.adminDatabaseRoom;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public interface IadminDatabaseRoom {

    public void addRoom(String nameRoom, String id_building, String nameEquipment);
    public void deleteRoom(String nameRoom);
    public void setId_building(String nameRoom, String id_building);
    public void setNameEquipment(String nameRoom, String nameEquipment);
}