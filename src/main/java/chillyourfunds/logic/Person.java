package chillyourfunds.logic;


import java.util.HashMap;
import java.util.Map;

public class Person{
    int id;
    int balance;
    String name;

    boolean isAdmin;

    Map<Person,Integer> mapOfExpenses=new HashMap<>();



    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person(int id, String name, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }
    void addToBalance(int x){
        balance+=x;
    }

    void payADebt(Person payer, int amount) {
        int debt = 0;
        for(int i = 0; i < mapOfExpenses.size(); i++) {
            if(mapOfExpenses.containsKey(payer)) {
                debt = mapOfExpenses.get(payer);
                if(debt - amount >= 0) {
                    mapOfExpenses.put(payer,debt - amount);
                    removeFromAList(payer);
                    subtractFromBalance(amount);
                    payer.addToBalance(amount);
                    break;
                } else {
                    System.out.println("Nie można zapłacić więcej niż wynosi twój dług");
                }
            } else {
                System.out.println("Nie ma takiego dłużnika");
                break;
            }
        }
    }


    void removeFromAList(Person payer) {
        if(mapOfExpenses.containsValue(0))
        mapOfExpenses.remove(payer, 0);
    }

    void showMyPayers() {
        if(mapOfExpenses.isEmpty()){
            System.out.println("Pusta lista, brak długów");
        } else {
            System.out.println("Lista długów osoby: "+name);
            for (Person person : mapOfExpenses.keySet()) {
                System.out.println(person.name + ": " + mapOfExpenses.get(person)+"$");
            }
//            System.out.println(mapOfExpenses);
        }
    }

    void subtractFromBalance(int x){
        balance-=x;
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}
