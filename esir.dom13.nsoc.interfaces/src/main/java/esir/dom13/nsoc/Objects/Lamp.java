package esir.dom13.nsoc.objects;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 13/11/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class Lamp {

    private String address;
    private String protocol;

    public Lamp(String addressHomeAutomation, String protocolHomeHomeAutomation ){

        address = addressHomeAutomation;
        protocol = protocolHomeHomeAutomation;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProtocol() {
        return protocol;
    }

}
