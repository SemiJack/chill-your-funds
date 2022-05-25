package ExpenseLogic;

public class EqualExpense extends Expense{
    public EqualExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }
    void equalSplit() {
        int amountPerPerson= amount/(debtors.size());
        for (int i = 0; i < debtors.size(); i++) {
            debtors.get(i).addToBalance(amountPerPerson);
        }
        payer.subtractFromBalance(amount);
    }

}
