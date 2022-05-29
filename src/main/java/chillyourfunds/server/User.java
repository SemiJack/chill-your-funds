package chillyourfunds.server;

import java.util.List;

public class User {
    private List<Integer> groupsID; // lista ID grup w których jest

    private final int UUID;// unikalne ID do rozpoznawania w dodawania do grupy
    private final String login;
    private final String password;

    private String firstName;

    private String lastName;

    public User(int uuid, String login, String password,String firstName, String lastName) {
        UUID = uuid;

        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUUID() {
        return UUID;
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
}


