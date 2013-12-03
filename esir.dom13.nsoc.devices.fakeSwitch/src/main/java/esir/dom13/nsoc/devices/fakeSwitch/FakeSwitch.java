/**
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package esir.dom13.nsoc.devices.fakeSwitch;

import esir.dom13.nsoc.devices.shutter.IManagementShutter;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.log.Log;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


@Library(name = "NSOC2013")
@Requires({
        @RequiredPort(name = "shutter", type = PortType.SERVICE, className = IManagementShutter.class, optional = true, needCheckDependency = false),
        @RequiredPort(name = "light", type = PortType.SERVICE, className = IManagementLight.class, optional = true, needCheckDependency = false)
})

@ComponentType
public class FakeSwitch extends AbstractComponentType {

    private static final int SWITCH_WIDTH = 50;
    private static final int SWITCH_HEIGHT = 100;
    private MyFrame frame = null;

    @Start
    public void start() {
        frame = new MyFrame("on", "off");
        frame.setVisible(true);
        if (this.isPortBinded("toggle")) {
//            this.getPortByName("toggle", ToggleLightService.class).toggle();
        }
    }

    @Stop
    public void stop() {
        frame.dispose();
        frame = null;
    }

    @Update
    public void update() {
        for (String s : this.getDictionary().keySet()) {
            Log.debug("Dic => " + s + " - " + this.getDictionary().get(s));
        }
    }

    private class MyFrame extends JFrame {

        private JButton on, off, toogle;
        private String onText;
        private String offText;

        public MyFrame(final String onText, final String offText) {

            this.onText = onText;
            this.offText = offText;
            //setPreferredSize(new Dimension(SWITCH_WIDTH, SWITCH_HEIGHT));
            //setLayout(new FlowLayout());
            on = new JButton(onText);
            on.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (isPortBinded("light")) {
                                getPortByName("light", IManagementLight.class).turnOn();
                            }
                            if(isPortBinded("shutter")){
                                getPortByName("shutter", IManagementShutter.class).setUp();
                            }
                        }
                    });
                }
            });

            off = new JButton(offText);
            off.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (isPortBinded("light")) {
                                getPortByName("light", IManagementLight.class).turnOff();

                            }
                            if(isPortBinded("shutter")){
                                getPortByName("shutter", IManagementShutter.class).setDown();
                            }
                        }
                    });
                }
            });
            toogle = new JButton("-?-");
            toogle.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (isPortBinded("light")) {
                                getPortByName("light", IManagementLight.class).toggle();

                            }
                            if (isPortBinded("shutter")) {
                                getPortByName("shutter", IManagementShutter.class).setIntermediate();

                            }
                        }
                    });

                }
            });
            ButtonGroup bg = new ButtonGroup();
            bg.add(on);
            bg.add(off);
            bg.add(toogle);

            setLayout(new FlowLayout());
            add(on);
            add(off);
            add(toogle);

            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            pack();
            setVisible(true);
        }

        @Override
        public void repaint() {
            on.setText(onText);
            off.setText(offText);
            super.repaint();
            super.revalidate();
        }

        /**
         * @param onText the onText to set
         */
        public final void setOnText(String onText) {
            this.onText = onText;
        }

        /**
         * @param offText the offText to set
         */
        public final void setOffText(String offText) {
            this.offText = offText;
        }
    }
}