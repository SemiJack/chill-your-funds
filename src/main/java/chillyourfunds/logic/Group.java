package chillyourfunds.logic;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Group {

    String groupName;

    Integer groupId;
    public static List<Person> people = new ArrayList<>();
    public List<Expense> expenses = new ArrayList<>();

    public Group(String groupName, Integer groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(Person person) {
        people.remove(person);
    }

//    public Person getPersonById(int id) {
//        Person p = null;
//        for (int i = 0; i < people.size(); i++) {
//            if (people.get(i).id == id) {
//                p = people.get(i);
//            }
//        }
//        return p;
//    }

    public static Person getPersonByName(String name) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).username.equals(name)) {
                p = people.get(i);
            }
        }
        return p;
    }

    public void simplifyGroupExpenses() {
        for(int i = 0; i < people.size(); i++) {
            people.get(i).mapOfExpenses.clear();
        }
        findPath(groupToSimplify());
    }

    private Map groupToSimplify() {
        Map<String,Double> mapOfBalancesInGroup = new HashMap<>();
        for(int i = 0; i < people.size(); i++) {
            mapOfBalancesInGroup.put(people.get(i).username, (double) -(people.get(i).balance));
        }

        return mapOfBalancesInGroup;
    }

    static List printBill;


        private static void findPath(Map details) {

            Double Max_Value = (Double) Collections.max(details.values());
            Double Min_Value = (Double) Collections.min(details.values());
            if (Max_Value != Min_Value) {
                String Max_Key = getKeyFromValue(details, Max_Value).toString();
                String Min_Key = getKeyFromValue(details, Min_Value).toString();
                Double result = Max_Value + Min_Value;
                result = round(result, 1);
                if ((result >= 0.0)) {

                    System.out.println(Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Min_Value), 2));
                    getPersonByName(Min_Key).mapOfExpenses.put(getPersonByName(Max_Key), (int) round(Math.abs(Min_Value), 2));
                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, result);
                    details.put(Min_Key, 0.0);
                } else {

                    System.out.println(Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
                    getPersonByName(Min_Key).mapOfExpenses.put(getPersonByName(Max_Key), (int) round(Math.abs(Max_Value), 2));
                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, 0.0);
                    details.put(Min_Key, result);
                }
                findPath(details);
            }
        }


        public static Object getKeyFromValue(Map hm, Double value) {
            for (Object o : hm.keySet()) {
                if (hm.get(o).equals(value)) {
                    return o;
                }
            }
            return null;
        }

        public static double round(double value, int places) {
            if (places < 0)
                throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

}
