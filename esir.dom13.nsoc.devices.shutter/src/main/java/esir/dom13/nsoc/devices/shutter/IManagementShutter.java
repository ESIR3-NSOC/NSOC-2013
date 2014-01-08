package esir.dom13.nsoc.devices.shutter;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 29/11/13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementShutter {
    void setUp();

    void setDown();

    void setIntermediate(/*Integer position*/);

    int getShutterState();

}
