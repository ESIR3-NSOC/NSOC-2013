package esir.dom13.nsoc.adminDatabaseBuilding;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public interface IadminDatabaseBuilding {

    public void addBuilding(String id_building, String nameBuilding, String numberOfRoom);
    public void deleteBuilding(String nameBuilding) ;
    public void setId_building(String nameBuilding, String id_building) ;
    public void setNumberOfRoom(String nameBuilding, String numberOfRoom) ;
    public String getName();
    public String getBuilding(String nameBuilding);
}
