package esir.dom13.nsoc.devices.fakeSwitch;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementLight {

    /**
     * Turning on the light of the room

     */
    void turnOn();

    /**
     * Turning off the light of the room

     */
    void turnOff();

    /**
     * Changing the state of the light

     */
    void toggle();

    boolean getState();
}
