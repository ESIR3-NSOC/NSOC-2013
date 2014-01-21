package esir.dom13.nsoc.devices.lectureBadge;

import org.kevoree.annotation.*;
import org.kevoree.tools.arduino.framework.AbstractPeriodicArduinoComponent;
import org.kevoree.tools.arduino.framework.ArduinoGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 27/11/13
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */

@Library(name = "Arduino")
@ComponentType
@Requires({
        @RequiredPort(name = "serial", type = PortType.MESSAGE,needCheckDependency = false)
})
public class TestArduino extends AbstractPeriodicArduinoComponent {

    @Override
    public void generateClassHeader(ArduinoGenerator gen) {


    }

    @Override
    public void generateInit(ArduinoGenerator gen)
    {
        gen.appendNativeStatement("Serial.begin(9600);\n");
    }

    @Override
    public void generatePeriodic(ArduinoGenerator arduinoGenerator)
    {
        getGenerator().appendNativeStatement("serial_rport(\"Test Arduino\");");
        getGenerator().appendNativeStatement("free(\"Test Arduino\");");
    }
}
