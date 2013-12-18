package esir.dom13.nsoc.idBadge;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Created by Clement on 18/12/13.
 */

@Library(name = "NSOC2013")

@Requires({
        @RequiredPort(name = "sendID", type = PortType.MESSAGE, optional = false)
})
@DictionaryType({
        @DictionaryAttribute(name = "portCOM_Windows", defaultValue = "COM3", optional = false),
        @DictionaryAttribute(name = "portCOM_Linux", defaultValue = "/dev/ttyUSB0", optional = false),
        @DictionaryAttribute(name = "portCOM_MACOS", defaultValue = "/dev/tty.usbserial-A9007UX1", optional = false),
        @DictionaryAttribute(name = "data_rate", defaultValue = "9600", optional = false)
})
@ComponentType
public class Identification extends AbstractComponentType implements SerialPortEventListener {
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

        getPortByName("sendID", MessagePort.class).process("START COMPONENT");
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

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * This should be called when you stop using the port.
     * This will prevent port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                System.out.println(inputLine);
                getPortByName("sendID", MessagePort.class).process(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

}
