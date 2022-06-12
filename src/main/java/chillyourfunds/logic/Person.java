package chillyourfunds.logic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Person implements Serializable {
    private int id;
    private String name;

    private boolean isAdmin; // TODO trzeba to przerobić, żeby admin odnosił się do jednej gruopy a nie to wszystkich

//    ArrayList<Integer> participateGroupsId = new ArrayList<>(); // groups' id in which person participates
    private Map<Group,Map<Person,Integer>> mapOfExpensesFromGroup = new HashMap<>();
    private Map<Group,Integer> mapOfBalances = new HashMap<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void addGroupIdToParticipate(int id) {
//        participateGroupsId.add(id);
//    }

//    public void removeGroupIdToParticipate(int id) {
//        participateGroupsId.remove(id);
//    }

//    public ArrayList<Integer> getParticipateGroupsId() {
//        return participateGroupsId;
//    }


    public Map<Group, Map<Person, Integer>> getMapOfExpensesFromGroup() {
        return mapOfExpensesFromGroup;
    }

    public void setMapOfExpensesFromGroup(Map<Group, Map<Person, Integer>> mapOfExpensesFromGroup) {
        this.mapOfExpensesFromGroup = mapOfExpensesFromGroup;
    }

    public Map<Group, Integer> getMapOfBalances() {
        return mapOfBalances;
    }

    public void setMapOfBalances(Map<Group, Integer> mapOfBalances) {
        this.mapOfBalances = mapOfBalances;
    }

    public Person(int id, String name) {
        this.id=id;
        this.name = name;
    }
    public Person(int id, String name, boolean isAdmin) {
        this.id=id;
        this.name = name;
        this.isAdmin = isAdmin;
    }
    void addToBalance(Group g,int x){
        if(mapOfBalances.containsKey(g)) {
            int output = 0;
            output = mapOfBalances.get(g) + x;
            mapOfBalances.put(g,output);
        } else {
            mapOfBalances.putIfAbsent(g,x);
        }
    }

   public void payADebt(Person payer, Group g, int amount) {
        int debt = 0;
        for(int i = 0; i < mapOfExpensesFromGroup.size(); i++) {
            if(mapOfExpensesFromGroup.containsKey(g)) {
                for(int j = 0; j < mapOfExpensesFromGroup.get(g).size(); j++) {
                    if(mapOfExpensesFromGroup.get(g).containsKey(payer)) {
                        debt = mapOfExpensesFromGroup.get(g).get(payer);
                        if(debt - amount >= 0) {
                            mapOfExpensesFromGroup.get(g).put(payer,debt - amount);
                            removeFromAList(payer,g);
                            subtractFromBalance(g,amount);
                            payer.addToBalance(g,amount);
                            break;
                        } else {
                            System.out.println("Nie można zapłacić więcej niż wynosi twój dług");
                        }
                    }
                }

            } else {
                System.out.println("Nie ma takiego dłużnika");
                break;
            }
        }
    }


    void removeFromAList(Person payer, Group group) {
        if(mapOfExpensesFromGroup.get(group).containsValue(0))
            mapOfExpensesFromGroup.get(group).remove(payer, 0);
    }

    void showMyPayers() {
        if(mapOfExpensesFromGroup.isEmpty()){
            System.out.println("Pusta lista, brak długów osoby: " + name);
        } else {
            System.out.println("Lista długów osoby: "+ name);
            for (Group group : mapOfExpensesFromGroup.keySet()) {
                System.out.println("Grupa: " + group.getGroupName());
                for(Person person : mapOfExpensesFromGroup.get(group).keySet()) {
                    System.out.println(person.name + ": " + mapOfExpensesFromGroup.get(group).get(person)+"$");
                }
            }
//            System.out.println(mapOfExpenses);
        }
    }

    void subtractFromBalance(Group g,int x){
        if(mapOfBalances.containsKey(g)) {
            int output = 0;
            output = mapOfBalances.get(g) - x;
            mapOfBalances.put(g,output);
        } else {
            mapOfBalances.putIfAbsent(g,-x);
        }
    }
    @Override
    public String toString() {
        return "Person{" +
                "username=" + id +
                ", name='" + name + '\'' +
                '}';
    }
    public void showMyBalances() {
        if(!mapOfBalances.isEmpty()) {
            for(Group group : mapOfBalances.keySet()) {
                System.out.println("Balans " + name + " w grupie " + group.getGroupName() + " wynosi: " + mapOfBalances.get(group));
            }
        }
    }

    public int getBalance(Group group) {
        if(mapOfBalances.containsKey(group)) {
            return mapOfBalances.get(group);
        }
        return 0;
    }

    public String getName() {
        return name;
    }
}
