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
     * Turn on the light on the room
     * @param light
     */
    void turnOn(Lamp light);

    /**
     * Turn off the light on the room
     * @param light
     */
    void turnOff(Lamp light);

    /**
     * Change state of light
     * @param light
     */
    void toggle(Lamp light);
}
