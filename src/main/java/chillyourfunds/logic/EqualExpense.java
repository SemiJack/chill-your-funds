package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;

public class EqualExpense extends Expense{
    public EqualExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }
    public void equalSplit() {
        int amountPerPerson = 0;
        if(amount % debtors.size() == 0) {
           amountPerPerson = amount / (debtors.size());
        } else {
            amountPerPerson = amount / (debtors.size());
            for (int i = 0; i < (amount % debtors.size()); i++) {
                if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                    debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer) + 1);
                } else {
                    Map<Person, Integer> map = new HashMap<>();
                    map.put(payer, 1);
                    debtors.get(i).getMapOfExpensesFromGroup().put(group, map);
                }
            }
        }

        for (int i = 0; i < debtors.size(); i++) {
            debtors.get(i).addToBalance(group, amountPerPerson);
            if (debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                if (debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)) {
                    debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer) + amountPerPerson);
                } else {
                    debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, amountPerPerson);
                }
            } else {
                Map<Person, Integer> map = new HashMap<>();
                map.put(payer, amountPerPerson);
                debtors.get(i).getMapOfExpensesFromGroup().put(group, map);
            }
        }
        payer.subtractFromBalance(group, amount);


    }

}
