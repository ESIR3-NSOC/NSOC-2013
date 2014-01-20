package esir.dom13.nsoc.arduinoLedOccupation;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 15/01/14
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */

@Provides({
        @ProvidedPort(name = "lightOccupation", type = PortType.MESSAGE),
})
@DictionaryType({
        @DictionaryAttribute(name = "portCOM_Windows", defaultValue = "COM3", optional = false),
        @DictionaryAttribute(name = "portCOM_Linux", defaultValue = "/dev/ttyUSB0", optional = false),
        @DictionaryAttribute(name = "portCOM_MACOS", defaultValue = "/dev/tty.usbserial-A9007UX1", optional = false),
        @DictionaryAttribute(name = "data_rate", defaultValue = "9600", optional = false)
})

@ComponentType
public class arduinoLed extends AbstractComponentType {

    SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    private String PORT_NAMES[] = new String[3];
    /**
     * A BufferedReader which will be fed by a InputStreamReader
     * converting the bytes into characters
     * making the displayed results codepage independent
     */
    private BufferedReader input;
    /**
     * The output stream to the port
     */
    private OutputStream output;
    /**
     * Milliseconds to block while waiting for port open
     */
    private final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private int DATA_RATE;

    @Start
    public void start() {

        getPortByName("lightOccupation", MessagePort.class).process("START COMPONENT");
        PORT_NAMES[0] = getDictionary().get("portCOM_MACOS").toString();
        PORT_NAMES[1] = getDictionary().get("portCOM_Linux").toString();
        PORT_NAMES[2] = getDictionary().get("portCOM_Windows").toString();
        DATA_RATE = Integer.parseInt(getDictionary().get("data_rate").toString());

        initialize();
        Thread t = new Thread() {
            public void run() {
                //the following line will keep this app alive for 1000 seconds,
                //waiting for events to occur and responding to them (printing incoming messages to console).
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException ie) {
                }
            }
        };
        t.start();
        System.out.println("Started");
    }

    @Stop
    public void stop() {
        close();
    }

    @Update
    public void update() {

    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        //First, Find an instance of serial port as set in PORT_NAMES.
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            for (String portName : PORT_NAMES) {
                if (currPortId.getName().equals(portName)) {
                    portId = currPortId;
                    break;
                }
            }
        }
        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

    }

    @Port(name = "lightOccupation")
    public void sendMessageToArduino(Object object) {
        boolean result = false;
        if (object instanceof Boolean) {
            result = ((Boolean) object).booleanValue();
        }

        if (serialPort != null) {
            if (result) {
                //TODO Allume
                try {
                    output.write(1);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            } else {
                //TODO eteindre
                try {
                    output.write(0);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }
}
