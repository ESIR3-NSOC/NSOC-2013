package esir.dom13.nsoc.databasePeople;

import com.sun.rowset.CachedRowSetImpl;
import esir.dom13.nsoc.database.IDatabaseConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

import com.mysql.jdbc.Driver;
import java.sql.*;
import java.sql.DriverManager;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Provides({
        @ProvidedPort(name = "getCursus", type = PortType.SERVICE, className = IDatabasePeople.class)
})

@Requires({
        @RequiredPort(name = "connectDatabase", type = PortType.SERVICE, className = IDatabaseConnection.class)
})

@ComponentType
public class DatabasePeople extends AbstractComponentType implements IDatabasePeople {



    @Start
    public void start() {

    }

    @Stop
    public void stop() {

    }


    @Update
    public void update() {

    }


    @Port(name = "getCursus",method = "getCursus")
    @Override
    public String getCursus(String id_rfid){

        String sql = "SELECT promo, options, specialite FROM IDatabasePeople where id_rfid =\"" + id_rfid + "\"";
        CachedRowSetImpl rs = null;
        Log.debug("IDatabasePeople ::: RFID = \""+id_rfid+"\"");

        String promo = null,options = null,specialite = null;

        rs = getPortByName("connectDatabase", IDatabaseConnection.class).sendRequestToDatabase(sql);

        try {
            while(rs.next()){
                //Retrieve by column name
                promo  = rs.getString("promo");
                options = rs.getString("options");
                specialite = rs.getString("specialite");

                //Display values
              Log.debug("promo: " + promo);
              Log.debug("options: " + options);
              Log.debug("specialite: " + specialite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JSONArray cursus = new JSONArray();
        try {
            cursus.put(0,promo);
            cursus.put(1, options);
            cursus.put(2, specialite);

        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return cursus.toString();
    }

    @Port(name = "getCursus",method = "getIdPeople")
    @Override
    public String getIdPeople(String id_rfid) {

        String sql;
        String id_people = null;


        String rfid = id_rfid;
        sql = "SELECT id_people FROM IDatabasePeople where id_rfid =\"" + rfid + "\"";

        Log.debug("IDatabasePeople ::: RFID = \""+rfid+"\"");

        //TODO
        ResultSet rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(sql);
        try {
            while(rs.next()){
                //Retrieve by column name
                id_people  = rs.getString("id_people");

                //Display values
                Log.debug("id_people: " + id_people);

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return id_people;
    }


    @Port(name = "getCursus",method = "getMailPeople")
    @Override
    public String getMailPeople(String id_people) {

        String sql;
        String emailAddress = null;


        sql = "SELECT emailAddress FROM IDatabasePeople where id_people =\"" + id_people + "\"";

        Log.debug("IDatabasePeople ::: id_people = \""+id_people+"\"");

        //TODO
        ResultSet rs = getPortByName("connectDatabase",IDatabaseConnection.class).sendRequestToDatabase(sql);
        try {
            while(rs.next()){
                //Retrieve by column name
                emailAddress  = rs.getString("emailAddress");

                //Display values
                Log.debug("emailAddress: " + emailAddress);

            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return emailAddress;
    }

}

