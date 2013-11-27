package esir.dom13.nsoc.devices.light.knx;

import esir.dom13.nsoc.devices.light.IManagementLight;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 20/11/13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */


@Library(name = "NSOC2013")
@Provides({
        @ProvidedPort(name = "CommandLight", type = PortType.SERVICE, className = IManagementLight.class)
})
@DictionaryType({
        @DictionaryAttribute(name = "address", defaultValue = "1/1/1", optional = false),
        @DictionaryAttribute(name = "name", defaultValue = "no_name", optional = true)
})
@ComponentType
public class Light_KNX extends AbstractComponentType implements IManagementLight {

    @Start
    public void start() {
        Log.debug("Component ManagementLights_KNX started");
    }

    @Stop
    public void stop() {

        Log.debug("Component ManagementLights_KNX stopped");
    }

    @Update
    public void update() {

        Log.debug("Component ManagementLights_KNX updated");
    }


    // implemented method

    @Port(name = "CommandLight", method = "turnOn")
    @Override
    public void turnOn() {
        String address = getDictionary().get("address").toString();
        Log.info("Lampe "+address +" allumé");
    }

    @Port(name = "CommandLight", method = "turnOff")
    @Override
    public void turnOff() {
        String address = getDictionary().get("address").toString();
        Log.info("Lampe "+address +" etteinte");
    }

    @Port(name = "CommandLight", method = "toggle")
    @Override
    public void toggle() {
        String address = getDictionary().get("address").toString();
        Log.info("Lampe "+address +" toggle");
    }

    @Port(name = "CommandLight", method = "getState")
    @Override
    public boolean getState() {
        String address = getDictionary().get("address").toString();

        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
