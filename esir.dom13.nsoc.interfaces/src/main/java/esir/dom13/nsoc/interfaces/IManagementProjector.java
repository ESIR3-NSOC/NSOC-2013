package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.Projector;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementProjector {

    void turnOn(Projector projector);

    void turnOff(Projector projector);

    void getState(Projector projector);
}
