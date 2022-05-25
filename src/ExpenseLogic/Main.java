package ExpenseLogic;

public class Main {
    public static void main(String[] args) {
        Person person1=new Person(1,"a","Eryk");
        Person person2=new Person(2,"b","Grzechu");
        Person person3=new Person(3,"c","Krzychu");
        Person person4=new Person(4,"d","Jacek");
        Service service=new Service();
        Group group=new Group();
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);
        service.split(service.createExpense(100,group,person1,group.people));
        System.out.println(person1.balance);
        System.out.println(person2.balance);
        System.out.println(person3.balance);
        System.out.println(person4.balance);
    }
}
