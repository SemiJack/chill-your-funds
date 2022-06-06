package chillyourfunds.server;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Person;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * author: Jacek Pelczar
 */
public class CYFData {
    private final HashMap<Integer, Group> groupData;

    private final HashMap<String, UserAccount> userData;


    public CYFData() {
        groupData = new HashMap<>();
        userData = new HashMap<>();

    }

    public boolean addGroup(String groupName, UserAccount creator) {
        Integer id = new Random().nextInt();
        if (groupData.containsKey(id)) {
            id = new Random().nextInt();
        }
        groupData.put(id, new Group(groupName, id));
        groupData.get(id).addPerson(creator.memberOfGroups);
        creator.memberOfGroups.addGroupIdToParticipate(id);
        System.out.println("pl");
        return groupData.get(id) != null;
    }

    public Group getGroup(Integer id) {
        return groupData.get(id);
    }

    public boolean addUser(String username, String password, String firstname, String lastname) {
        int credentialsHash = Objects.hash(username, password);
        if (!userData.containsKey(username)) {
            userData.put(username, new UserAccount(Objects.hash(username), credentialsHash, username, firstname, lastname));
            return true;
        } else {
            return false;
        }
    }
    public Person getPersonByUsername(String username){
        return userData.get(username).memberOfGroups;
    }

    public UserAccount getUserByCredentials(String username, String password) {
        UserAccount temp = userData.get(username);
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
