package chillyourfunds.server;

import chillyourfunds.logic.Person;

import java.io.Serializable;
import java.util.ArrayList;

public class UserAccount implements Serializable {

    private final int uuid; // unique user id

    private final String username;

    private final int credentials;

    private final String firstName;
    private final String lastName;
    Person memberOfGroups;


    public UserAccount(int uuid, int credentials, String username, String firstName, String lastName) {
        this.uuid = uuid;
        this.credentials = credentials;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberOfGroups = new Person(uuid, this.username);
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


    public String getLastName() {
        return lastName;
    }

    public boolean checkCredentials(int credentials) {
        return this.credentials == credentials;
    }

}


