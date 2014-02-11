package esir.dom13.nsoc.databaseHistory;

/**
 * Created by Clement on 15/01/14.
 */
public interface IDatabaseHistory {

    void putEntry(String id_people, String teacher, String lesson,String salle, String batiment, String dateHours);
    public String getHistory(String id_people);
}
