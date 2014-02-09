package esir.dom13.nsoc.adminDatabaseOption;

/**
 * Created with IntelliJ IDEA.
 * User: Yvan
 * Date: 04/02/14
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
public interface IadminDatabaseOption {
    public void addOption(String option);
    public void addSpecialite(String option, String specialite);
    public void deleteOption(String option);
    public void deleteSpecialite(String specialite);
    public String getSpecialite(String option);
    public String getOption();
}
