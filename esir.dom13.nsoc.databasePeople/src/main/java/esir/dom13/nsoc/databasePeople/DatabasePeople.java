package esir.dom13.nsoc.databasePeople;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")


@Provides({
        @ProvidedPort(name = "getCursus", type = PortType.SERVICE, className = IDatabasePeople.class)
})

@DictionaryType({
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),
        @DictionaryAttribute(name = "DB_URL", defaultValue = "jdbc:mysql://localhost/projetnsoc", optional = false),
        @DictionaryAttribute(name = "USER", defaultValue = "PASS", optional = false),
        @DictionaryAttribute(name = "PASS", defaultValue = "", optional = false),
        @DictionaryAttribute(name = "JDBC_DRIVER", defaultValue = "com.mysql.jdbc.Driver", optional = false),

})
@ComponentType
public class DatabasePeople extends AbstractComponentType implements IDatabasePeople {

    // JDBC driver name and database URL
    private String JDBC_DRIVER ,DB_URL;

    //  Database credentials
    private String USER,PASS;


    @Start
    public void start() {
        JDBC_DRIVER = getPortByName("JDBC_DRIVER").toString();
        DB_URL = getPortByName("DB_URL").toString();
        USER = getPortByName("DB_URL").toString();
        PASS = getPortByName("PASS").toString();
    }

    @Stop
    public void stop() {

    }

    @Update
    public void update() {
        JDBC_DRIVER = getPortByName("JDBC_DRIVER").toString();
        DB_URL = getPortByName("DB_URL").toString();
        USER = getPortByName("DB_URL").toString();
        PASS = getPortByName("PASS").toString();
    }

    @Port(name = "getCursus")
    public String getCursus(String id_rfid) {
        return null;
    }
}

