package esir.dom13.nsoc.devices.light.knx;


import esir.dom13.nsoc.devices.light.IManagementLight;
import org.kevoree.framework.AbstractComponentType;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 20/11/13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public class Light_KNX extends AbstractComponentType implements IManagementLight {

    @Override
    public void turnOn() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void turnOff() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void toggle() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getState() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
