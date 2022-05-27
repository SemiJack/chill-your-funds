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
                    debtors.get(i).subtractFromBalance(amount);
                    debtors.get(i).addToBalance(part);
                    counter += part;
                } else{
                    debtors.get(i).addToBalance(mapOfAmounts.get(debtors.get(i)));
                    if(debtors.get(i).mapOfExpenses.containsKey(payer)){
                        debtors.get(i).mapOfExpenses.put(payer,debtors.get(i).mapOfExpenses.get(payer)+part);
                    }else{
                        debtors.get(i).mapOfExpenses.putIfAbsent(payer,part);
                    }

                    counter += part;
                }
            }
        } else {
            payer.subtractFromBalance(amount);
            for(int i = 0; i < debtors.size(); i++) {
                part = scanner.nextInt();
                mapOfAmounts.put(debtors.get(i), part);
                debtors.get(i).addToBalance(mapOfAmounts.get(debtors.get(i)));
                if(debtors.get(i).mapOfExpenses.containsKey(payer)){
                    debtors.get(i).mapOfExpenses.put(payer,debtors.get(i).mapOfExpenses.get(payer)+part);
                }else{
                    debtors.get(i).mapOfExpenses.putIfAbsent(payer,part);
                }
                counter += part;
            }
        }
        if(counter != amount) {
            System.out.println("Błąd"); //Potrzebny jest exception!!!!!!!
        }
    }

}
