package chillyourfunds.logic;

import java.util.List;
//póki co niepotrzebna klasa
public class Service {
    void split(Expense expense){
        int amountPerPerson= expense.amount/(expense.debtors.size());
        for (int i = 0; i < expense.debtors.size(); i++) {
            expense.debtors.get(i).addToBalance(amountPerPerson);
        }
        expense.payer.subtractFromBalance(expense.amount);
        expense.group.expenses.add(expense);
    }

}