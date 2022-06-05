package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PercentExpense extends Expense {
    public PercentExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    private Map<Person, Integer> mapOfPercents = new HashMap<Person, Integer>();

    void percentSplit() throws WrongPercentException {
        Scanner scanner = new Scanner(System.in);
        int percent = 0;
        int counter = 0;
        if (isPayerADebtor()) {
            for (int i = 0; i < debtors.size(); i++) {
                percent = scanner.nextInt();
                mapOfPercents.put(debtors.get(i), percent);
                counter += percent;
            }
            if (counter == 100) {
                for (int i = 0; i < debtors.size(); i++) {
                    if (debtors.get(i) == payer) {
                        debtors.get(i).subtractFromBalance(group, amount);
                        debtors.get(i).addToBalance(group, percent * amount / 100);
                    } else {
                        debtors.get(i).addToBalance(group, mapOfPercents.get(debtors.get(i)) * amount / 100);
                        if (debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                            if (debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)) {
                                debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer) + mapOfPercents.get(debtors.get(i)) * amount / 100);
                            } else {
                                debtors.get(i).getMapOfExpensesFromGroup().get(group).putIfAbsent(payer, mapOfPercents.get(debtors.get(i)) * amount / 100);
                            }
                        } else {
                            Map<Person, Integer> map = new HashMap<>();
                            map.put(payer, mapOfPercents.get(debtors.get(i)) * amount / 100);
                            debtors.get(i).getMapOfExpensesFromGroup().put(group, map);
                        }
                    }
                }
            } else {
                throw new WrongPercentException("Zły podział procentów");
            }
        } else {
            for (int i = 0; i < debtors.size(); i++) {
                percent = scanner.nextInt();
                mapOfPercents.put(debtors.get(i), percent);
                counter += percent;
            }
            if (counter == 100) {
                payer.subtractFromBalance(group, amount);
                for (int i = 0; i < debtors.size(); i++) {
                    debtors.get(i).addToBalance(group, mapOfPercents.get(debtors.get(i)) * amount / 100);
                    if (debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                        if (debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)) {
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer) + mapOfPercents.get(debtors.get(i)) * amount / 100);
                        } else {
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).putIfAbsent(payer, mapOfPercents.get(debtors.get(i)) * amount / 100);
                        }
                    } else {
                        Map<Person, Integer> map = new HashMap<>();
                        map.put(payer, mapOfPercents.get(debtors.get(i)) * amount / 100);
                        debtors.get(i).getMapOfExpensesFromGroup().put(group, map);
                    }
                }
            } else {
                throw new WrongPercentException("Zły podział procentów");
            }
        }
    }
}
