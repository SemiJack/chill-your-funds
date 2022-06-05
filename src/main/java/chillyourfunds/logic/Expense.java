package chillyourfunds.logic;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    int amount;
    Group group;
    Person payer;
    boolean isPaid = false;
    List<Person> debtors = new ArrayList<>();

    public Expense(int amount, Group group, Person payer) {
        this.amount = amount;
        this.group = group;
        this.payer = payer;
    }

    public void addDebtor(int id) {
        if(group.getPersonById(id) != null) {
            debtors.add(group.getPersonById(id));
        } else {
            System.out.println("Nie można dodać osoby spoza grupy jako dłużnika!");
        }

    }

    public void createExpense(Expense e) {
        group.getExpenses().add(e);
    }

    public boolean isPayerADebtor() {
        for (int i = 0; i < debtors.size(); i++) {
            if(debtors.get(i).equals(payer)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "amount=" + amount +
                ", groupName=" + group.getGroupName() +
                ", payer=" + payer +
                ", debtors=" + debtors +
                '}';
    }
}
