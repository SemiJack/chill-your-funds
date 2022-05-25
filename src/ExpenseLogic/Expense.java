package ExpenseLogic;

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
        debtors.add(group.getPersonById(id));
    }

    void createExpense(Expense e) {
        group.expenses.add(e);
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
                ", groupName=" + group.groupName +
                ", payer=" + payer +
                ", debtors=" + debtors +
                '}';
    }

    //    boolean ifSimplify;
//    String typeOfSplit;
}
