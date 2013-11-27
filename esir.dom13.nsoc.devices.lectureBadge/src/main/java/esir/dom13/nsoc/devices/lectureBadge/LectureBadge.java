package esir.dom13.nsoc.devices.lectureBadge;

import org.kevoree.annotation.*;
import org.kevoree.tools.arduino.framework.AbstractPeriodicArduinoComponent;
import org.kevoree.tools.arduino.framework.ArduinoGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 27/11/13
 * Time: 15:55
 * To change this template use File | Settings | File Templates.
 */


@Library(name = "Arduino")
@ComponentType
public class LectureBadge extends AbstractPeriodicArduinoComponent {

    @Override
    public void generateClassHeader(ArduinoGenerator gen) {
        gen.appendNativeStatement("#include <SoftwareSerial.h>\n");
        gen.appendNativeStatement("#include <SeeedRFIDLib.h>\n");
        gen.appendNativeStatement("#define RFID_RX_PIN 2\n");
        gen.appendNativeStatement("#define RFID_TX_PIN 3\n");
        gen.appendNativeStatement("SeeedRFIDLib RFID(RFID_RX_PIN, RFID_TX_PIN);\n");
        gen.appendNativeStatement("RFIDTag tag;\n");

    }

    @Override
    public void generateInit(ArduinoGenerator gen)
    {
        gen.appendNativeStatement("Serial.begin(57600);\n");
    }

    @Override
    public void generatePeriodic(ArduinoGenerator arduinoGenerator)
    {

        getGenerator().appendNativeStatement("if(RFID.isIdAvailable()) {\n");
        getGenerator().appendNativeStatement("tag = RFID.readId();\n");
        getGenerator().appendNativeStatement("Serial.print(\"ID:       \");\n");
        getGenerator().appendNativeStatement("Serial.println(tag.id);\n");
        getGenerator().appendNativeStatement("Serial.print(\"ID (HEX): \");\n");
        getGenerator().appendNativeStatement("Serial.println(tag.raw);\n");
        getGenerator().appendNativeStatement("String id = tag.raw;\n");

    }
}

