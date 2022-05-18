package ExpenseLogic;

public class Debt {
    Person borrower,lender;
    double amount;

    public Person getBorrower() {
        return borrower;
    }

    public void setBorrower(Person borrower) {
        this.borrower = borrower;
    }

    public Person getLender() {
        return lender;
    }

    public void setLender(Person lender) {
        this.lender = lender;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
