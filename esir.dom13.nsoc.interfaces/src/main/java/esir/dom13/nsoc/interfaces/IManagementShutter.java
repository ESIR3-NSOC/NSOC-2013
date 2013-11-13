package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Screen;
import esir.dom13.nsoc.objects.Shutter;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementShutter {

    void pullUp(Shutter screen);

    void pullDown(Shutter screen);

    void getState(Shutter screen);
}
