package esir.dom13.nsoc.interfaces;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 20/11/13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public interface ICommunicationKNX {

   void writeBoolean (String address, boolean value);

   void readBoolean(String address);


}
