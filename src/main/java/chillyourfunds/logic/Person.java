package chillyourfunds.logic;


import java.util.HashMap;
import java.util.Map;

public class Person{
    String username;
    int balance;
    String firstname;

    boolean isAdmin;

    Map<Person,Integer> mapOfExpenses=new HashMap<>();



    public Person(String username, String firstname) {
        this.username = username;
        this.firstname = firstname;
    }
    public Person(String username, String name, boolean isAdmin) {
        this.username = username;
        this.firstname = name;
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
            System.out.println("Lista długów osoby: "+firstname);
            for (Person person : mapOfExpenses.keySet()) {
                System.out.println(person.firstname + ": " + mapOfExpenses.get(person)+"$");
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
                "username=" + username +
                ", balance=" + balance +
                ", firstname='" + firstname + '\'' +
                '}';
    }

    public int getBalance() {
        return balance;
    }

    public String getFirstname() {
        return firstname;
    }
}
