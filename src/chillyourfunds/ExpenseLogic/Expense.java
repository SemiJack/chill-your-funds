package chillyourfunds.ExpenseLogic;

import java.util.List;

public class Expense {
    String currency;
    double amount;
    int noOfPeople;
    List<Person> personsInExpense;
    List<Debt> debtList;
    Person theOneWhoPays;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNoOfPeople() {
        return noOfPeople;
    }

    public List<Person> getPersonsInExpense() {
        return personsInExpense;
    }

    public void setPersonsInExpense(List<Person> personsInExpense) {
        this.personsInExpense = personsInExpense;
    }

    public List<Debt> getDebtList() {
        return debtList;
    }

    public void setDebtList(List<Debt> debtList) {
        this.debtList = debtList;
    }

    public Person getTheOneWhoPays() {
        return theOneWhoPays;
    }

    public void setTheOneWhoPays(Person theOneWhoPays) {
        this.theOneWhoPays = theOneWhoPays;
    }
}
