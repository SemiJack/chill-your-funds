package chillyourfunds.logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Group implements Serializable{
    /**
     * Klasa Group jest reprezentacją grupy, wewnątrz której realizowane są wydatki.
     * Parametrami grupy są nazwa grupy, identyfikator grupy, lista osób, które są członkami grupy
     * i lista wydatków grupy, na podstawie których wyliczane są kwoty do zwrotu.
     */
    private String groupName;

    private Integer groupId;
    private List<Person> people = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public String getGroupName() {
        return groupName;
    }


    public Integer getGroupId() {
        return groupId;
    }


    public List<Person> getPeople() {
        return people;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     *
     * @param groupName nazwa grupy
     * @param groupId identyfikator grupy
     */
    public Group(String groupName, Integer groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    /**
     * Metoda addPerson dodaje osobę do danej grupy
     * @param person obiekt osoby, która jest dodawana do grupy
     */

    public void addPerson(Person person) {
        if(!people.contains(person)){
            people.add(person);
            person.addGroupIdToParticipate(groupId);
            System.out.println("powinno dodac");
        }
    }

    /**
     * Metoda removePerson usuwa osobę z grupy w wypadku gdy jej bilans wyjdzie na 0.
     * @param person obiekt osoby, która jest usuwana
     * @return zwraca wartość boolean dla stanu czy osoba zastaje usunięta, czy nie
     */
    public boolean removePerson(Person person) {
        if(person.getBalance(this)== 0){
            people.remove(person);
            person.removeGroupIdToParticipate(groupId);
            return true;
        }else return false;
    }

    /**
     * Metoda getPersonById pozwala wyszukać osobę po numerze id.
     * @param id identyfikator przypisany osobie
     * @return zwraca obiekt osoby, której id zostało wprowadzone
     */
    public Person getPersonById(int id) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                p = people.get(i);
            }
        }
        return p;
    }

    /**
     * Metoda getPersonById pozwala wyszukać osobę po numerze id.
     * @param name imię osoby
     * @return zwraca obiekt osoby, której id zostało wprowadzone
     */
    public  Person getPersonByName(String name) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().equals(name)) {
                p = people.get(i);
            }
        }
        return p;
    }

    /**
     * Metoda simplifyGroupExpenses inicjuje operację, która upraszcza proces zwrotu pieniędzy.
     * Znajduje ona koszty, które można pominąć poprzez przekazanie opłat no inne osoby i
     * zminimalizowaniu ilości operacji.
     * @param group obiekt grupy, w której upraszczamy zwroty
     */
    public void simplifyGroupExpenses(Group group) {
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getMapOfExpensesFromGroup().get(group)!=null) {
                people.get(i).getMapOfExpensesFromGroup().get(group).clear();
            }
        }
        findPath(groupToSimplify(group),group);
    }

    /**
     * Metoda groupToSimplify bierze grupę, której zwroty ma uprościć i zwraca mapę wydatków tej grupy.
     * @param group obiekt grupy, której wydatki mamy uprościć
     * @return mapa wydatków grupy
     */

    private Map groupToSimplify(Group group) {
        Map<String,Double> mapOfBalancesInGroup = new HashMap<>();
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getMapOfBalances().get(group) != null) {
                mapOfBalancesInGroup.put(people.get(i).getName(), (double) -(people.get(i).getMapOfBalances().get(group)));
            }
        }

        return mapOfBalancesInGroup;
    }

    /**
     * Metoda findPath znajduje możliwości uproszczenia zwrotów i upraszcza mapę wydatków.
     * @param details mapa wydatków grupy do uproszczenia
     * @param group grupa, której zwroty są upraszczane
     */

        private void findPath(Map details, Group group) {

            Double Max_Value = (Double) Collections.max(details.values());
            Double Min_Value = (Double) Collections.min(details.values());
            if (Max_Value != Min_Value) {
                String Max_Key = getKeyFromValue(details, Max_Value).toString();
                String Min_Key = getKeyFromValue(details, Min_Value).toString();
                Double result = Max_Value + Min_Value;
                result = round(result, 1);
                if ((result >= 0.0)) {
                    System.out.println((Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Min_Value), 2)));
                    getPersonByName(Min_Key).getMapOfExpensesFromGroup().get(group).put(getPersonByName(Max_Key), (int) round(Math.abs(Min_Value), 2));
                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, result);
                    details.put(Min_Key, 0.0);
                } else {
                    System.out.println(Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
                    getPersonByName(Min_Key).getMapOfExpensesFromGroup().get(group).put(getPersonByName(Max_Key), (int) round(Math.abs(Max_Value), 2));
                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, 0.0);
                    details.put(Min_Key, result);
                }
                findPath(details,group);
            }
        }

    /**
     * Metoda getKeyFromValue na podstawie hash mapy i otrzymanej wartości
     * zwraca obiekt klucza dla szukanej wartości.
     * @param hm mapa wydatków
     * @param value szukana wartość
     * @return obiekt klucza
     */

        public static Object getKeyFromValue(Map hm, Double value) {
            for (Object o : hm.keySet()) {
                if (hm.get(o).equals(value)) {
                    return o;
                }
            }
            return null;
        }

    /**
     * Metoda round zaokrągla podaną wartość
     * @param value podana wartość
     * @param places miejsce, do którego zaokrąglana jest funkcja
     * @return
     */
        public static double round(double value, int places) {
            if (places < 0)
                throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
}
