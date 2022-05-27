package chillyourfunds.server;

import java.util.List;

public class User {
    private List<Integer> groupsID; // lista ID grup w których jest

    private final int UUID; // unikalne ID do rozpoznawania w dodawania do grupy

    public User(List<Integer> groupsID, int uuid) {
        this.groupsID = groupsID;
        UUID = uuid;
    }

    public int getUUID() {
        return UUID;
    }
}
