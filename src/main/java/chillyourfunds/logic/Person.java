package chillyourfunds.logic;


import java.util.HashMap;
import java.util.Map;

public class Person{
    int id;
    public String email;
    int balance;
    String name;

    Map<Person,Integer> mapOfExpenses=new HashMap<>();

    public Person(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    void addToBalance(int x){
        balance+=x;
    }

    void payADebt(Person payer, int amount) {
        int debt = 0;
        for(int i = 0; i < mapOfExpenses.size(); i++) {
            if(mapOfExpenses.containsKey(payer)) {
                debt = mapOfExpenses.get(payer);
                if(debt - amount > 0) {
                    mapOfExpenses.put(payer,debt - amount);
                    removeFromAList(payer);
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
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
