package esir.dom13.nsoc.devices.shutter.knx;

import esir.dom13.nsoc.communication.knx.ICommunicationKNX;
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
@Requires({
        @RequiredPort(name = "Com_KNX", type = PortType.SERVICE, className = ICommunicationKNX.class)
})
@DictionaryType({
        @DictionaryAttribute(name = "address", defaultValue = "2/1/1", optional = false) ,
        @DictionaryAttribute(name = "delay", defaultValue = "10000",optional = true)
})
@ComponentType
public class Shutter_KNX extends AbstractComponentType implements IManagementShutter {

    private String address;

    @Start
    public void start() {
        address = getDictionary().get("address").toString();
        Log.debug("Component ManagementShutter_KNX started");
    }

    @Stop
    public void stop() {

        Log.debug("Component ManagementShutter_KNX stopped");
    }

    @Update
    public void update() {
        address = getDictionary().get("address").toString();
        Log.debug("Component ManagementShutter_KNX updated");
    }


    // implemented method

    @Port(name = "CommandShutter", method = "setUp")
    @Override
    public void setUp() {
        boolean value = false;
        getPortByName("Com_KNX",ICommunicationKNX.class).writeBoolean(address, value);
        Log.info("Shutter "+address +" set up");
    }

    @Port(name = "CommandShutter", method = "setDown")
    @Override
    public void setDown() {
        boolean value = true;
        getPortByName("Com_KNX",ICommunicationKNX.class).writeBoolean(address, value);
        Log.info("Shutter "+address +" set down");
    }

    @Port(name = "CommandShutter", method = "setIntermediate")
    @Override
    public void setIntermediate(Integer position) {
        Log.info("Shutter "+address +" set intermediate");
        setUp();
        try {
            Thread.sleep(Integer.parseInt(getDictionary().get("delay").toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setDown();
        try {
            Thread.sleep(Integer.parseInt(getDictionary().get("delay").toString())/position);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setUp();
    }

    @Port(name = "CommandShutter", method = "getState")
    @Override
    public int getState() {
        boolean value = getPortByName("Com_KNX",ICommunicationKNX.class).readBoolean(address);
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
