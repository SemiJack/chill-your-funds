package chillyourfunds.server;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Person;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    ArrayList<Integer> groupsId = new ArrayList<>();
    private final int uuid;
    private final String username;

    private final int credentials;

    private String firstName;
    private String lastName;
    Person InGroup;


    public User(int uuid, int credentials, String username, String firstName, String lastName) {
        this.uuid = uuid;
        this.credentials = credentials;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.InGroup = new Person(uuid, this.username);
    }

    public Person getMeInGroup() {
        return InGroup;
    }

    public String getUsername() {
        return username;
    }

    public int getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean checkCredentials(int credentials) {
        return this.credentials == credentials;
    }

    public void addToGroup(int id) {
        groupsId.add(id);
    }

    public void removeFromGroup(int id) {
        groupsId.remove(id);
    }

    public ArrayList<Integer> getGroupsId() {
        return groupsId;
    }
}


