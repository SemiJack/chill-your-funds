package ExpenseLogic;


public class Person{
    int id;
    public String email;
    int balance;
    String name;

    public Person(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    void addToBalance(int x){
        balance+=x;
    }

    void subtractFromBalance(int x){
        balance-=x;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
