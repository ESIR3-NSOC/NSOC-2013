package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.People;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 15/11/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public interface IUsersCommunication {

    void sendNotificationByEmail(People people, String Message, String Object);

}
