package chillyourfunds.server;

import java.util.List;
import java.util.Objects;

public class User {
    private List<Integer> groupsID; // lista ID grup w których jest

    private final String username;

    private final int credentials;

    private String firstName;

    private String lastName;

    public User(int credentials, String username,String firstName, String lastName) {
        this.credentials = credentials;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
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

    public boolean checkCredentials(int credentials){
        return this.credentials == credentials;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}


