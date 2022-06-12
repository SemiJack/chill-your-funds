package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * Klasa realizuje podział kosztów w ramach danego wydatku według dokładnych kwot. Dziedziczy po klasie Expense.
 * Jej parametrami są wartość poniesionego kosztu, grupa, wewnątrz której wydatek jest realizowany,
 * a także osoba, która ponosi dany koszt. Klasa ta przypisuje każdemu uwzględnionemu członkowi
 * grupy wartość kosztu, jaki powinien zwrócić osobie płacącej. Operuje ona na Hash Mapie,
 * za pomocą której przypisywane są wartości podzielonych kwot do dłużników.
 */
public class ExactExpense extends Expense{
    public ExactExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    public Map<Person, Integer> mapOfAmounts = new HashMap<Person,Integer>();
    public void exactSplit(Map<Integer,Integer> mapOfDebtorsWithAmount) throws WrongAmountException {
        int counter = 0;

        if(isPayerADebtor()){
            for(int i = 0; i < debtors.size(); i++) {
                mapOfAmounts.put(debtors.get(i),mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                counter += mapOfDebtorsWithAmount.get(debtors.get(i).getId());
            }
            if(counter == amount) {
                for(int i = 0; i < debtors.size(); i++) {
                    if(debtors.get(i) == payer) {
                        debtors.get(i).subtractFromBalance(group,amount);
                        debtors.get(i).addToBalance(group,mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null && debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)) {
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer,debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer)+mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        } else {
                            Map<Person,Integer> map = new HashMap<>();
                            map.put(payer,mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                            debtors.get(i).getMapOfExpensesFromGroup().putIfAbsent(group,map);
                        }
                    } else {
                        debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                        if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null && debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)){
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer)+mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        }else{
                            Map<Person,Integer> map = new HashMap<>();
                            map.put(payer,mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                            debtors.get(i).getMapOfExpensesFromGroup().putIfAbsent(group,map);
                        }

                    }
                }
            } else {
                throw new WrongAmountException("Zły przydział kwot");
            }

        } else {

            for (int i = 0; i < debtors.size(); i++) {
                mapOfAmounts.put(debtors.get(i), mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                counter+=mapOfDebtorsWithAmount.get(debtors.get(i).getId());
            }
            if(counter == amount) {
                payer.subtractFromBalance(group,amount);
                for(int i = 0; i < debtors.size(); i++) {
                    debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                    if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                        if(debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)){
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer)+mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        }else{
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).putIfAbsent(payer,mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        }
                    } else {
                        Map<Person, Integer> map = new HashMap<>();
                        map.put(payer,mapOfDebtorsWithAmount.get(debtors.get(i).getId()));
                        debtors.get(i).getMapOfExpensesFromGroup().put(group,map);
                    }

                }
            } else {
                throw new WrongAmountException("Zły podział kwot");
            }

        }

    }

}
