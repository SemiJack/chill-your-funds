package chillyourfunds.server;

import chillyourfunds.logic.Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa, która reprezentuje konto użytkownika w usłudze ChillYourFunds
 */
public class UserAccount implements Serializable {

    private final int uuid; // unikalny identyfikator użytkownika

    private final String username;
    /*
    Integer, który jest wynikiem hashowania username i password,
    tworzony jest przy rejestracji. Na serwerze nie jest przechowywane hasło
    */
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


