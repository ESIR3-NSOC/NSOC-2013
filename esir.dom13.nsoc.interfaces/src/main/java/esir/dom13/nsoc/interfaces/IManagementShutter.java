package esir.dom13.nsoc.interfaces;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:28
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementShutter {

    void setUp();

    void setDown();

    void setIntermediate(int position);

    void stop();

    int getState();
}
