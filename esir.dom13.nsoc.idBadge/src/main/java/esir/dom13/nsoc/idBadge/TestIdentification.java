package esir.dom13.nsoc.idBadge;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;

/**
 * Created by Clement on 26/01/14.
 */


@Requires({
        @RequiredPort(name = "sendGACHE", type = PortType.MESSAGE, optional = true),
        @RequiredPort(name = "sendLED", type = PortType.MESSAGE, optional = true)
})
@ComponentType
public class TestIdentification extends AbstractComponentType {

    @Start
    public void start() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getPortByName("sendGACHE", MessagePort.class).process("authorized");
            getPortByName("sendLED", MessagePort.class).process(true);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getPortByName("sendLED", MessagePort.class).process(false);
            getPortByName("sendGACHE", MessagePort.class).process("NOTauthorized");
        }
    }

    @Update
    public void update() {


    }

    @Stop
    public void stop() {

    }
}
