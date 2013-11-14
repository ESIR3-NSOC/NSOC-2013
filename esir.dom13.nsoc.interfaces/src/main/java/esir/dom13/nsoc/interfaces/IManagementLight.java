package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Lamp;

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
     * @param light
     */
    void turnOn(Lamp light);

    /**
     * Turning off the light of the room
     * @param light
     */
    void turnOff(Lamp light);

    /**
     * Changing the state of the light
     * @param light
     */
    void toggle(Lamp light);
}
