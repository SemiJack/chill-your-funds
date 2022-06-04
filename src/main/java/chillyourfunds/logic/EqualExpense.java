package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;

public class EqualExpense extends Expense{
    public EqualExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }
    public void equalSplit() {
        int amountPerPerson = amount / (debtors.size());
        for (int i = 0; i < debtors.size(); i++) {
            //debtors.get(i).addToBalance(amountPerPerson);
            debtors.get(i).addToBalance(group, amountPerPerson);
            if (debtors.get(i).mapOfExpensesFromGroup.get(group) != null) {
                if (debtors.get(i).mapOfExpensesFromGroup.get(group).containsKey(payer)) {
                    debtors.get(i).mapOfExpensesFromGroup.get(group).put(payer, debtors.get(i).mapOfExpensesFromGroup.get(group).get(payer) + amountPerPerson);
                } else {
                    debtors.get(i).mapOfExpensesFromGroup.get(group).put(payer, amountPerPerson);
                }
            } else {
                Map<Person, Integer> map = new HashMap<>();
                map.put(payer, amountPerPerson);
                debtors.get(i).mapOfExpensesFromGroup.put(group, map);
            }
        }
        payer.subtractFromBalance(group, amount);
    }

}
