package chillyourfunds.logic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Group implements Serializable{

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


    public Group(String groupName, Integer groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
    }

    public void addPerson(Person person) {
        if(!people.contains(person)){
            people.add(person);
            person.addGroupIdToParticipate(groupId);
            System.out.println("powinno dodac");
        }
    }

    public boolean removePerson(Person person) {
        if(person.getBalance(this)== 0){
            people.remove(person);
            person.removeGroupIdToParticipate(groupId);
            return true;
        }else return false;
    }

    public Person getPersonById(int id) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getId() == id) {
                p = people.get(i);
            }
        }
        return p;
    }

    public  Person getPersonByName(String name) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).getName().equals(name)) {
                p = people.get(i);
            }
        }
        return p;
    }

    public void simplifyGroupExpenses(Group group) {
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getMapOfExpensesFromGroup().get(group)!=null) {
                people.get(i).getMapOfExpensesFromGroup().get(group).clear();
            }
        }
        findPath(groupToSimplify(group),group);
    }

    private Map groupToSimplify(Group group) {
        Map<String,Double> mapOfBalancesInGroup = new HashMap<>();
        for(int i = 0; i < people.size(); i++) {
            if(people.get(i).getMapOfBalances().get(group) != null) {
                mapOfBalancesInGroup.put(people.get(i).getName(), (double) -(people.get(i).getMapOfBalances().get(group)));
            }
        }

        return mapOfBalancesInGroup;
    }




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
