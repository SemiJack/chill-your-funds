package chillyourfunds.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ExactExpense extends Expense{
    public ExactExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    Map<Person, Integer> mapOfAmounts = new HashMap<Person,Integer>();
    void exactSplit() {
        Scanner scanner = new Scanner(System.in);
        int part = 0;
        int counter = 0;
        if(isPayerADebtor()){
            for(int i = 0; i < debtors.size(); i++) {
                part = scanner.nextInt();
                mapOfAmounts.put(debtors.get(i), part);
                if(debtors.get(i) == payer) {
                    debtors.get(i).subtractFromBalance(group,amount);
                    debtors.get(i).addToBalance(group,part);
                    counter += part;
                } else{
                    debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                    if(debtors.get(i).mapOfExpensesFromGroup.get(group).containsKey(payer) && debtors.get(i).mapOfExpensesFromGroup.get(group) != null ){
                        debtors.get(i).mapOfExpensesFromGroup.get(group).put(payer,debtors.get(i).mapOfExpensesFromGroup.get(group).get(payer)+part);
                    }else{
                        debtors.get(i).mapOfExpensesFromGroup.get(group).putIfAbsent(payer,part);
                    }

                    counter += part;
                }
            }
        } else {
            payer.subtractFromBalance(group,amount);
            for(int i = 0; i < debtors.size(); i++) {
                part = scanner.nextInt();
                mapOfAmounts.put(debtors.get(i), part);
                debtors.get(i).addToBalance(group,mapOfAmounts.get(debtors.get(i)));
                if(debtors.get(i).mapOfExpensesFromGroup.get(group) != null) {
                    if(debtors.get(i).mapOfExpensesFromGroup.get(group).containsKey(payer)){
                        debtors.get(i).mapOfExpensesFromGroup.get(group).put(payer,debtors.get(i).mapOfExpensesFromGroup.get(group).get(payer)+part);
                    }else{
                        debtors.get(i).mapOfExpensesFromGroup.get(group).putIfAbsent(payer,part);
                    }
                } else {
                    Map<Person, Integer> map = new HashMap<>();
                    map.put(payer,part);
                    debtors.get(i).mapOfExpensesFromGroup.put(group,map);
                }
                counter += part;
            }
        }
        if(counter != amount) {
            System.out.println("Błąd"); //Potrzebny jest exception!!!!!!!
        }
    }

}
