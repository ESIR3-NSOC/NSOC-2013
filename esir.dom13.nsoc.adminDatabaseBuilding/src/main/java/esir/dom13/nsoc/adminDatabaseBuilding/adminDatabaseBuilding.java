package esir.dom13.nsoc.adminDatabaseBuilding;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 27/01/14
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "setDatabaseBuilding", type = PortType.SERVICE, className = IadminDatabaseBuilding.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class adminDatabaseBuilding extends AbstractComponentType implements IadminDatabaseBuilding {


    @Start
    public void start() {


    }

    @Stop
    public void stop() {


    }

    @Update
    public void update() {


    }

    @Port(name = "setDatabaseBuilding", method = "addBuilding")
    @Override
    public void addBuilding(String id_building, String nameBuilding, String numberOfRoom) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "INSERT INTO `idatabasebuilding` (`id_building`, `nameBuilding`, `numberOfRoom`)" +
                " VALUES ('" +  id_building + "', '" + nameBuilding + "', '" + numberOfRoom + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseBuilding", method = "deleteBuilding")
    @Override
    public void deleteBuilding(String nameBuilding) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "DELETE FROM `idatabasebuilding` WHERE nameBuilding = '"+ nameBuilding + "')";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseBuilding", method = "setId_building")
    @Override
    public void setId_building(String nameBuilding, String id_building) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "UPDATE `idatabasebuilding` SET `id_building` = '" + id_building +"' WHERE nameBuilding = '" + nameBuilding + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseBuilding", method = "setNumberOfRoom")
    @Override
    public void setNumberOfRoom(String nameBuilding, String numberOfRoom) {
        //To change body of implemented methods use File | Settings | File Templates.
        String sql = "UPDATE `idatabasebuilding` SET `numberOfRoom` = '" + numberOfRoom +"' WHERE nameBuilding = '" + nameBuilding + "'";
        //peut etre pas de guillemmet pour la valeur de remplacement
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);
    }

    @Port(name = "setDatabaseBuilding", method = "getNameBuilding")
    @Override
    public String getNameBuilding(){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT `nameBuilding` FROM `idatabasebuilding`";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while (rs.next()){
                value = rs.getString("nameBuilding");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }

    @Port(name = "setDatabaseBuilding", method = "getBuilding")
    @Override
    public String getBuilding(String nameBuilding){

        String value = null;
        JSONArray tableau = new JSONArray();
        String sql = "SELECT * FROM `idatabasebuilding` WHERE nameBuilding = '" + nameBuilding + "'";
        CachedRowSetImpl rs = null;
        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while(rs.next()){
            value = rs.getString("id_building");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
                value = rs.getString("numberOfRoom");   //voir si cela fonctionne et qu'on récupère valeur Bdd
                tableau.put(value);
            }
            }
         catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return tableau.toString();
    }
}
