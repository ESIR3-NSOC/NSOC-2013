package esir.dom13.nsoc.interfaces;

import esir.dom13.nsoc.objects.ElectricStrike;

/**
 * Created with IntelliJ IDEA.
 * User: Renaud
 * Date: 12/11/13
 * Time: 15:33
 * To change this template use File | Settings | File Templates.
 */
public interface IManagementElectricStrike {

    void open( ElectricStrike electricStrike);

    void close( ElectricStrike electricStrike);
}
