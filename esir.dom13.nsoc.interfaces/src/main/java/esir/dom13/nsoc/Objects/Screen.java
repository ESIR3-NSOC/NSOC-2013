package esir.dom13.nsoc.objects;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 13/11/13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public class Screen {

    private String address;
    private String protocol;

    public Screen(String address, String protocol) {
        this.address = address;
        this.protocol = protocol;
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
