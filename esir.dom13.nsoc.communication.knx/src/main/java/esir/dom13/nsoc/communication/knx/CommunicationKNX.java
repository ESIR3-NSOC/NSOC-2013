package esir.dom13.nsoc.communication.knx;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;

import org.kevoree.log.Log;
import tuwien.auto.calimero.GroupAddress;
import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicator;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

import java.net.InetSocketAddress;



/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 29/11/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "NSOC2013")
@DictionaryType({
        @DictionaryAttribute(name = "ip_gateway", defaultValue = "192.168.1.1", optional = false),
        @DictionaryAttribute(name = "ip_host", defaultValue = "192.168.1.100",optional = false)
})
@Provides({
        @ProvidedPort(name = "ComKNX", type = PortType.SERVICE, className = ICommunicationKNX.class)
})
@ComponentType
public class CommunicationKNX extends AbstractComponentType implements ICommunicationKNX{

    private String ip_gateway;
    private String ip_host;
    private ProcessCommunicator pc;
    private KNXNetworkLinkIP netLinkIp;


    @Start
    public void start() {
        ip_gateway = getDictionary().get("ip_gateway").toString();
        ip_host = getDictionary().get("ip_host").toString();
        Log.debug("Component CommunicationKNX started");
    }

    @Stop
    public void stop() {

        Log.debug("Component CommunicationKNX stopped");
    }

    @Update
    public void update() {
        ip_gateway = getDictionary().get("ip_gateway").toString();
        ip_host = getDictionary().get("ip_host").toString();
        Log.debug("Component CommunicationKNX updated");
    }




    @Override
    @Port(name = "ComKNX", method = "writeKNXBoolean")
    public void writeKNXBoolean(String address, Boolean value) {
        try {
            connect();
            pc.write(new GroupAddress(address),value);
            Log.info("writeBoolean on GroupAddress : "+address+"  ::  value : "+value);
        } catch (KNXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.error("Can't connect to KNX gateway");
        }
        disconnect();
    }


    @Port(name = "ComKNX", method = "readKNXBoolean")
    @Override
    public Boolean readKNXBoolean(String address) {
        boolean value = false;
        try {
            connect();
            value = pc.readBool(new GroupAddress(address));
            Log.info("ReadBool on GroupAddress : "+address);

        } catch (KNXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.error("Can't connect to KNX gateway");
        }
        disconnect();
        return value;
    }

    @Override
    @Port(name = "ComKNX", method = "writeKNXFloat")
    public void writeKNXFloat(String address, Float value) {
        try {
            connect();
            pc.write(new GroupAddress(address),value);
            Log.info("writeFloat on GroupAddress : "+address+"  ::  value : "+value);
        } catch (KNXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.error("Can't connect to KNX gateway");
        }
        disconnect();
    }


    @Port(name = "ComKNX", method = "readKNXFloat")
    @Override
    public Float readKNXFloat(String address) {
        float value = 0;
        try {
            connect();
            value = pc.readFloat(new GroupAddress(address));
            Log.info("ReadFloat on GroupAddress : "+address);

        } catch (KNXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            Log.error("Can't connect to KNX gateway");
        }
        disconnect();
        return value;
    }

    public void disconnect() {
        netLinkIp.close();
    }

    public void connect() throws KNXException {

        Log.debug("Connexion a la passerelle");


        // Création de la connexion KNX
        netLinkIp = new KNXNetworkLinkIP( KNXNetworkLinkIP.TUNNEL, new InetSocketAddress(ip_host, 0 ), new InetSocketAddress(ip_gateway, 3671 ), false, new TPSettings(
                false ) );
        pc = new ProcessCommunicatorImpl( netLinkIp );

    }
}
