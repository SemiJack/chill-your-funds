package chillyourfunds.server;

import chillyourfunds.logic.Group;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class CYFData {
    private final HashMap<Integer, Group> groupData;

    private final HashMap<String, User> userData;


    public CYFData() {
        groupData = new HashMap<>();
        userData = new HashMap<>();

    }

    public boolean addGroup(String groupName, User creator) {
        Integer id = new Random().nextInt();
        if (groupData.containsKey(id)) {
            id = new Random().nextInt();
        }
        groupData.put(id, new Group(groupName, id));
        groupData.get(id).addPerson(creator.InGroup);
        creator.addToGroup(id);
        System.out.println("pl");
        return groupData.get(id) != null;
    }

    public Group getGroup(Integer id) {
        return groupData.get(id);
    }

    public boolean addUser(String username, String password, String firstname, String lastname) {
        int credentialsHash = Objects.hash(username, password);
        if (!userData.containsKey(username)) {
            userData.put(username, new User(Objects.hash(username), credentialsHash, username, firstname, lastname));
            return true;
        } else {
            return false;
        }
    }

    public User getUserByCredentials(String username, String password) {
        User temp = userData.get(username);
        if (temp != null && temp.checkCredentials(Objects.hash(username, password))) {
            return temp;
        } else return null;
    }


//
//    public static void main(String[] args) {
//        Group gr = new Group("dddsd");
//        HashMap<Integer, LinkedList<Expense>> database = new HashMap<>();
//        Person payer = new Person(32323, "sasas@gmail.com","jacek");
//        Person payer2 = new Person(32523, "sasas@gmyl.com","kolega");
//        LinkedList<Expense> koo = new LinkedList<>();
//        koo.add(new Expense(233,gr,payer2));
//        database.put(12,koo);
//
//        database.get(12).add(new Expense(3453,gr,payer));
//
//        System.out.println();
//
//        System.out.println(database.get(12));
//    }

}
