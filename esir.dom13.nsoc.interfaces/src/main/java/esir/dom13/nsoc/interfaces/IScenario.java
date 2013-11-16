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

    void projectorScn();

    void goodByeScn();

    void helloScn();

    void turnOnLightsScn ();

    void turnOffLightsScn ();

    void setDownShutterScn ();

    void setUpShutterScn ();

    void autoSetBrightnessScn(int brightness);

}
