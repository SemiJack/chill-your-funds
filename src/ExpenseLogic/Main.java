package ExpenseLogic;

public class Main {
    public static void main(String[] args) {
        Person person1=new Person(1,"a","Eryk");
        Person person2=new Person(2,"b","Grzechu");
        Person person3=new Person(3,"c","Krzychu");
        Person person4=new Person(4,"d","Jacek");
        Service service=new Service();
        Group group=new Group("szefy");
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);

        EqualExpense e1 = new EqualExpense(100, group, person1);
        EqualExpense e2 = new EqualExpense(150, group, person1);
        EqualExpense e3 = new EqualExpense(200, group, person1);
        e1.addDebtor(1);
        e1.addDebtor(2);
        e1.addDebtor(3);
        e1.addDebtor(4);
        e1.createExpense(e1);
        e2.addDebtor(3);
        e2.addDebtor(4);
        e2.addDebtor(2);
        e2.createExpense(e2);
        e3.addDebtor(1);
        e3.addDebtor(2);
        e3.addDebtor(3);
        e3.addDebtor(4);
        e3.createExpense(e3);
        e1.equalSplit();
        e2.equalSplit();
        e3.equalSplit();


        ExactExpense e5 = new ExactExpense(100, group,person1);
        e5.addDebtor(2);
        e5.addDebtor(3);
        e5.exactSplit();
        e5.createExpense(e5);

        PercentExpense e6 = new PercentExpense(200,group,person1);
        e6.addDebtor(3);
        e6.addDebtor(2);
        e6.percentSplit();
        e6.createExpense(e6);
        System.out.println(person1.balance);
        System.out.println(person2.balance);
        System.out.println(person3.balance);
    }
}
