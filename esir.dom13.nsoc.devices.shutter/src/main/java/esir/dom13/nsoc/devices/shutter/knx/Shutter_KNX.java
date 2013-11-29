package esir.dom13.nsoc.devices.shutter.knx;

import esir.dom13.nsoc.devices.shutter.IManagementShutter;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 29/11/13
 * Time: 15:46
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "NSOC2013")
@Provides({
        @ProvidedPort(name = "CommandShutter", type = PortType.SERVICE, className = IManagementShutter.class)
})
@DictionaryType({
        @DictionaryAttribute(name = "address", defaultValue = "2/1/1", optional = false),
        @DictionaryAttribute(name = "name", defaultValue = "no_name", optional = true)
})
@ComponentType
public class Shutter_KNX extends AbstractComponentType implements IManagementShutter {

    @Start
    public void start() {
        Log.debug("Component ManagementShutter_KNX started");
    }

    @Stop
    public void stop() {

        Log.debug("Component ManagementShutter_KNX stopped");
    }

    @Update
    public void update() {

        Log.debug("Component ManagementShutter_KNX updated");
    }


    // implemented method

    @Port(name = "CommandShutter", method = "setUp")
    @Override
    public void setUp() {
        String address = getDictionary().get("address").toString();
        Log.info("Shutter "+address +" set up");
    }

    @Port(name = "CommandShutter", method = "setDown")
    @Override
    public void setDown() {
        String address = getDictionary().get("address").toString();
        Log.info("Shutter "+address +" set down");
    }

    @Port(name = "CommandShutter", method = "setIntermediate")
    @Override
    public void setIntermediate(int position) {
        String address = getDictionary().get("address").toString();
        Log.info("Shutter "+address +" set intermediate");
    }

    @Port(name = "CommandShutter", method = "getState")
    @Override
    public int getState() {
        String address = getDictionary().get("address").toString();
        //todo
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
