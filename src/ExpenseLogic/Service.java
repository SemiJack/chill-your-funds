package ExpenseLogic;

import java.util.List;

public class Service {
    Expense createExpense(int amount, Group group, Person payer, List<Person> debtors){
         return new Expense(amount,group,payer,debtors);
    }
    void split(Expense expense){
        int amountPerPerson= expense.amount/(expense.debtors.size());
        for (int i = 0; i < expense.debtors.size(); i++) {
            expense.debtors.get(i).addToBalance(amountPerPerson);
        }
        expense.payer.subtractFromBalance(expense.amount);
    }

}
