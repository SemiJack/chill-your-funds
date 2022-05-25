package ExpenseLogic;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    int amount;
    Group group;
    Person payer;
    boolean isPaid=false;
    boolean isPayerADebtor;
    List<Person> debtors=new ArrayList<>();

    public Expense(int amount, Group group, Person payer,List<Person> debtors) {
        this.amount = amount;
        this.group = group;
        this.payer = payer;
        this.debtors=debtors;
    }

    void addExpense(Expense expense){

    }
    //    boolean ifSimplify;
//    String typeOfSplit;
}
