package esir.dom13.nsoc.communication.knx;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 20/11/13
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */
public interface ICommunicationKNX {

   void writeKNXBoolean(String address, Boolean value);

   Boolean readKNXBoolean(String address);

   public void writeKNXFloat(String address, Float value);

   public Float readKNXFloat(String address);




}
