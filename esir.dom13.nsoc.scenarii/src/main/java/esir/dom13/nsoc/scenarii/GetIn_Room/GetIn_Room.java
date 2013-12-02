package esir.dom13.nsoc.scenarii.GetIn_Room;

import esir.dom13.nsoc.devices.light.IManagementLight;
import esir.dom13.nsoc.devices.shutter.IManagementShutter;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 02/12/13
 * Time: 18:56
 * To change this template use File | Settings | File Templates.
 */
@Library(name = "NSOC2013")
@Provides({
        @ProvidedPort(name = "StartScenario", type = PortType.MESSAGE)
})
@Requires({
        @RequiredPort(name = "CommandShutter", type = PortType.SERVICE, className = IManagementShutter.class),
        @RequiredPort(name = "CommandLight", type = PortType.SERVICE, className = IManagementLight.class)
})

@ComponentType
public class GetIn_Room extends AbstractComponentType {

    @Start
    public void start() {
    }

    @Stop
    public void stop() {
    }

    @Update
    public void update() {
    }

    @Port(name="StartScenario")
    public void startScenario(){

    }

}
