package esir.dom13.nsoc.communication.knx;

import jet.runtime.typeinfo.KotlinSignature;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 20/11/13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public interface ICommunicationKNX {

   void writeBoolean(String address, Boolean value);

   Boolean readBoolean(String address);


}
