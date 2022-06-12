package chillyourfunds.logic;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Klasa PercentExpense realizuje podział kosztów w ramach danego wydatku według wartości procentowych.
 * Dziedziczy po klasie Expense. Jej parametrami są wartość poniesionego kosztu, grupa, wewnątrz której
 * wydatek jest realizowany, a także osoba, która ponosi dany koszt. Klasa ta przypisuje każdemu
 * uwzględnionemu członkowi grupy wartość kosztu, jaki powinien zwrócić osobie płacącej. Operuje ona na
 * Hash Mapie, za pomocą której przypisywane są wartości podzielonych kwot do dłużników.
 */

public class PercentExpense extends Expense implements Serializable {
    public PercentExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    private Map<Person, Integer> mapOfPercents = new HashMap<Person, Integer>();

    void percentSplit(Map<Integer,Integer> mapOfDebtorsWithPercents) throws WrongPercentException {
        int percent = 0;
        int counter = 0;
        if (isPayerADebtor()) {
            for (int i = 0; i < debtors.size(); i++) {
                percent = mapOfDebtorsWithPercents.get(debtors.get(i).getId());
                mapOfPercents.put(debtors.get(i), percent);
                counter += percent;
            }
            if (counter == 100) {
                for (int i = 0; i < debtors.size(); i++) {
                    if (debtors.get(i) == payer) {
                        debtors.get(i).subtractFromBalance(group, amount);
                        debtors.get(i).addToBalance(group, percent * amount / 100);
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
                percent = mapOfDebtorsWithPercents.get(debtors.get(i).getId());
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
