package esir.dom13.nsoc.interfaces;


import esir.dom13.nsoc.objects.Sensor;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementSensor {

    Object getState(Sensor sensor);

    void detectChange(Sensor sensor);
}
