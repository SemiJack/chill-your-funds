package chillyourfunds.ExpenseLogic;

import java.util.List;

public class Person {
    int id;
    String name, user,type;
    List<Debt> balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Debt> getBalance() {
        return balance;
    }

    public void setBalance(List<Debt> balance) {
        this.balance = balance;
    }
}
