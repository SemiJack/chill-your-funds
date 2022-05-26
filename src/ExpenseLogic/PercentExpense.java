package ExpenseLogic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PercentExpense extends Expense{
    public PercentExpense(int amount, Group group, Person payer) {
        super(amount, group, payer);
    }

    Map<Person, Integer> mapOfPercents = new HashMap<Person,Integer>();
    void percentSplit() {
        Scanner scanner = new Scanner(System.in);
        int percent = 0;
        int counter = 0;
        if(isPayerADebtor()){
            for(int i = 0; i < debtors.size(); i++) {
                percent = scanner.nextInt();
                mapOfPercents.put(debtors.get(i), percent);
                if(debtors.get(i) == payer) {
                    debtors.get(i).subtractFromBalance(amount);
                    debtors.get(i).addToBalance(percent*amount/100);
                    counter += percent;
                } else{
                    debtors.get(i).addToBalance(mapOfPercents.get(debtors.get(i))*amount/100);
                    if(debtors.get(i).mapOfExpenses.containsKey(payer)){
                        debtors.get(i).mapOfExpenses.put(payer,debtors.get(i).mapOfExpenses.get(payer)+mapOfPercents.get(debtors.get(i))*amount/100);
                    }else{
                        debtors.get(i).mapOfExpenses.putIfAbsent(payer,mapOfPercents.get(debtors.get(i))*amount/100);
                    }

                    counter += percent;
                }
            }
        } else {
            payer.subtractFromBalance(amount);
            for(int i = 0; i < debtors.size(); i++) {
                percent = scanner.nextInt();
                mapOfPercents.put(debtors.get(i), percent);
                debtors.get(i).addToBalance(mapOfPercents.get(debtors.get(i))*amount/100);
                if(debtors.get(i).mapOfExpenses.containsKey(payer)){
                    debtors.get(i).mapOfExpenses.put(payer,debtors.get(i).mapOfExpenses.get(payer)+mapOfPercents.get(debtors.get(i))*amount/100);
                }else{
                    debtors.get(i).mapOfExpenses.putIfAbsent(payer,mapOfPercents.get(debtors.get(i))*amount/100);
                }

                counter += percent;
            }
        }
        if(counter != 100) {
            System.out.println("Błąd"); //Potrzebny jest exception!!!!!!!
        }
    }

}
