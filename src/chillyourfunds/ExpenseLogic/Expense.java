package chillyourfunds.ExpenseLogic;

import java.util.List;

public class Expense {
    String currency;
    Split splitType;
    double amount;
    int numberOfPeople;
    List<String> peopleInExpense;
    List<Debt> debtList;
    Person theOneWhoPays;

    public Person getPaidBy(){
        return theOneWhoPays;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }
}
