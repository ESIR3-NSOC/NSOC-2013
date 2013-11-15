package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Room;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */
public interface IScenario {

    void projectorScn(Room room);

    void goodByeScn(Room room);

    void helloScn(Room room);

    void turnOnLightsScn (Room room);

    void turnOffLightsScn (Room room);

    void setDownShutterScn (Room room);

    void setUpShutterScn (Room room);

    void autoSetBrightnessScn(Room room, int brightness);

}
