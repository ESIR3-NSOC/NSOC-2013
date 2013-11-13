package esir.dom13.nsoc.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 13/11/13
 * Time: 17:07
 * To change this template use File | Settings | File Templates.
 */
public class Lamp {

    private String adress;
    private String protocol;

    public Lamp(String adressHomeAutomation, String protocolHomeHomeAutomation ){

        adress = adressHomeAutomation;
        protocol = protocolHomeHomeAutomation;

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
