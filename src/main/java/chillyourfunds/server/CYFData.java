package chillyourfunds.server;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Person;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;


public class CYFData {
    private HashMap<Integer, Group> groupData; //struktura danych do przechowwywania wszystkich transakcji w historii? w pliku? w arrayliście? w linkedliście? w streamie? w bazie danych??

    private HashMap<Integer,User> userData;


    public CYFData() {
        groupData = new HashMap<>();
        userData = new HashMap<>();

    }
    public void addGroup( String groupName, User creator){
        Integer id = new Random().nextInt();
        if(groupData.containsKey(id)){
            id = new Random().nextInt();
        }
        groupData.put(id,new Group(groupName));
        groupData.get(id).addPerson(new Person(creator.getUUID(), creator.getFirstName(),true));
    }

    public Group getGroup(Integer id){
        return  groupData.get(id);
    }

    public void addUser(String login, String password, String firstname, String lastname){
        int credentials = Objects.hash(login,password);
        userData.put(credentials,new User(credentials, login, password, firstname, lastname));
        System.out.println("fw");
    }

    public User getUserByCredentials(String login, String password){
        return userData.get(Objects.hash(login,password));
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
