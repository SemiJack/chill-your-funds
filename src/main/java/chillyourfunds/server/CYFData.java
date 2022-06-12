package chillyourfunds.server;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Person;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * Ta klasa realizuje zadanie przechowywania danych jako "baza danych".
 * @author Jacek Pelczar
 */
public class CYFData {
    private final HashMap<Integer, Group> groupData;

    private final HashMap<String, UserAccount> userData;


    public CYFData() {
        groupData = new HashMap<>();
        userData = new HashMap<>();

    }

    /**
     * Dodaje nową grupę do bazy danych
     * @param groupName Nazwa grupy
     * @param creator Twórca grupy
     * @return Utworzona grupa
     */
    public Group addGroup(String groupName, UserAccount creator) {
        Integer id = new Random().nextInt();
        if (groupData.containsKey(id)) {
            id = new Random().nextInt();
        }
        groupData.put(id, new Group(groupName, id));
        groupData.get(id).addPerson(creator.memberOfGroups);
        return groupData.get(id);
    }

    public Group getGroup(Integer id) {
        return groupData.get(id);
    }

    /**
     * Tworzy konto użytkownika (User Account)
     * @param username nazwa użytkownika
     * @param password hasło
     * @param firstname Imię
     * @param lastname Nazwisko
     * @return czy operacj się udała
     */
    public boolean addUser(String username, String password, String firstname, String lastname) {
        int credentialsHash = Objects.hash(username, password);
        if (!userData.containsKey(username)) {
            userData.put(username, new UserAccount(Objects.hash(username), credentialsHash, username, firstname, lastname));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Zwraca obioekt klasy Person dla użytkownika o podanej nazwie
     * @param username nazwa użytkownika
     * @return
     */
    public Person getPersonByUsername(String username){
        return userData.get(username).memberOfGroups;
    }

    /**
     * Zwraca obiekt User Account jeżeli podano poprawne dane logowania
     * @param username nazwa użytkownika
     * @param password hasło
     * @return
     */
    public UserAccount getUserByCredentials(String username, String password) {
        UserAccount temp = userData.get(username);
        if (temp != null && temp.checkCredentials(Objects.hash(username, password))) {
            return temp;
        } else return null;
    }

}
