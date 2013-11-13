package esir.dom13.nsoc.objects;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 13/11/13
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class Shutter {

    private String adress;
    private String protocol;

    public Shutter(String adress, String protocol) {
        this.adress = adress;
        this.protocol = protocol;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getProtocol() {
        return protocol;
    }

}
