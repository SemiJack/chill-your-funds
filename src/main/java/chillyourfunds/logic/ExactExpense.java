package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExactExpense extends Expense{
    public ExactExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    private Map<Person, Integer> mapOfAmounts = new HashMap<Person,Integer>();
    void exactSplit() throws WrongAmountException {
        Scanner scanner = new Scanner(System.in);
        int part = 0;
        int counter = 0;

        if(isPayerADebtor()){
            for(int i = 0; i < debtors.size(); i++) {
            part = (int)(scanner.nextDouble()*100);
            mapOfAmounts.put(debtors.get(i), part);
            counter += part;
            }
            if(counter == amount) {
                for(int i = 0; i < debtors.size(); i++) {
                    if(debtors.get(i) == payer) {
                        debtors.get(i).subtractFromBalance(group,amount);
                        debtors.get(i).addToBalance(group,part);
                    } else {
                        debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                        if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null && debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)){
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer)+part);
                        }else{
                            Map<Person,Integer> map = new HashMap<>();
                            map.put(payer,part);
                            debtors.get(i).getMapOfExpensesFromGroup().putIfAbsent(group,map);
                        }

                    }
                }
            } else {
                throw new WrongAmountException("Zły przydział kwot");
            }

        } else {

            for (int i = 0; i < debtors.size(); i++) {
                part = scanner.nextInt();
                mapOfAmounts.put(debtors.get(i), part);
                counter+=part;
            }
            if(counter == amount) {
                payer.subtractFromBalance(group,amount);
                for(int i = 0; i < debtors.size(); i++) {
                    debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                    if(debtors.get(i).getMapOfExpensesFromGroup().get(group) != null) {
                        if(debtors.get(i).getMapOfExpensesFromGroup().get(group).containsKey(payer)){
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).put(payer, debtors.get(i).getMapOfExpensesFromGroup().get(group).get(payer)+part);
                        }else{
                            debtors.get(i).getMapOfExpensesFromGroup().get(group).putIfAbsent(payer,part);
                        }
                    } else {
                        Map<Person, Integer> map = new HashMap<>();
                        map.put(payer,part);
                        debtors.get(i).getMapOfExpensesFromGroup().put(group,map);
                    }

                }
            } else {
                throw new WrongAmountException("Zły podział kwot");
            }

        }

    }

}
