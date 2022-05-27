package chillyourfunds.logic;

public class EqualExpense extends Expense{
    public EqualExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }
    void equalSplit() {
        int amountPerPerson= amount/(debtors.size());
        for (int i = 0; i < debtors.size(); i++) {
            debtors.get(i).addToBalance(amountPerPerson);
            if(debtors.get(i).mapOfExpenses.containsKey(payer)){
                debtors.get(i).mapOfExpenses.put(payer,debtors.get(i).mapOfExpenses.get(payer)+amountPerPerson);
            }else{
                debtors.get(i).mapOfExpenses.putIfAbsent(payer,amountPerPerson);
            }

        }
        payer.subtractFromBalance(amount);
    }

}
