package esir.dom13.nsoc.objects;

/**
 * Created with IntelliJ IDEA.
 * User: Clement
 * Date: 13/11/13
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public class People {


    String firstName;
    String surname;
    int id_people;
    String emailAddress;
    String id_rfid;

    public People(int id_people){
        this.id_people = id_people;
    }


    public People(String firstName, String surname, int id_people, String emailAddress) {
        this.firstName = firstName;
        this.surname = surname;
        this.id_people = id_people;
        this.emailAddress = emailAddress;
    }

    public People(int id_people, String id_rfid, String emailAddress) {
        this.id_people = id_people;
        this.id_rfid = id_rfid;
        this.emailAddress = emailAddress;
    }


    public String getName() {
        return firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId_people() {
        return id_people;
    }

    public void setId_people(int id_people) {
        this.id_people = id_people;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getId_rfid() {
        return id_rfid;
    }

    public void setId_rfid(String id_rfid) {
        this.id_rfid = id_rfid;
    }



}
