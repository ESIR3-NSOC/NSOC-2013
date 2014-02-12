package esir.dom13.nsoc.adminDatabaseRoom;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public interface IadminDatabaseRoom {

    public void addRoom(String nameRoom, String nameBuilding, String id_Building, String nameEquipment);
    public void deleteRoom(String nameRoom, String nameBuilding);
    public void setId_building(String nameRoom, String id_building);
    public void setNameEquipment(String nameBuilding, String nameRoom, String nameEquipment);
    public String getName(String nameBuilding);
    public String getSalle(String nameBuilding,String nameSalle);
}
