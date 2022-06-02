package chillyourfunds.logic;

public class LogicTest {
    public static void main(String[] args) {
        Person person1=new Person("eric","Eryk");
        Person person2=new Person("gregor","Grzechu");
        Person person3=new Person("kris","Krzychu");
        Person person4=new Person("json","Jacek");
//        Service service=new Service();
        Group group=new Group("szefy",997);
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);

        EqualExpense e1 = new EqualExpense(100, group, person1);
        EqualExpense e2 = new EqualExpense(150, group, person2);
        EqualExpense e3 = new EqualExpense(200, group, person3);
        e1.addDebtor("eric");
        e1.addDebtor("gregor");
        e1.addDebtor("kris");
        e1.addDebtor("json");
        e1.createExpense(e1);
        System.out.println("------------------");
        e2.addDebtor("gregor");
        e2.addDebtor("kris");
        e2.addDebtor("json");
        e2.createExpense(e2);
        e3.addDebtor("eric");
        e3.addDebtor("gregor");
        e3.addDebtor("kris");
        e3.addDebtor("json");
        e3.createExpense(e3);
        e1.equalSplit();
        e2.equalSplit();
        e3.equalSplit();
//
//
//        ExactExpense e5 = new ExactExpense(100, group,person4);
//        e5.addDebtor(2);
//        e5.addDebtor(3);
//        e5.exactSplit();
//        e5.createExpense(e5);
//
//        PercentExpense e6 = new PercentExpense(200,group,person4);
//        e6.addDebtor(1);
//        e6.addDebtor(2);
//        e6.addDebtor(3);
//        e6.percentSplit();
//        e6.createExpense(e6);
        System.out.println("Stan konta "+person1.username+" wynosi "+person1.balance+"$");
        System.out.println("Stan konta "+person2.username+" wynosi "+person2.balance+"$");
        System.out.println("Stan konta "+person3.username+" wynosi "+person3.balance+"$");
        System.out.println("Stan konta "+person4.username+" wynosi "+person4.balance+"$");
        System.out.println();
//        System.out.println(person2.name+" jest winien "+person1.name+" "+person2.mapOfExpenses.get(person1)+"$");
//        System.out.println(person2.name+" jest winien "+person4.name+" "+person2.mapOfExpenses.get(person4)+"$");
//
        person1.showMyPayers();
        person2.showMyPayers();
        person3.showMyPayers();
        person4.showMyPayers();

//       person4.payADebt(person1,25);
//
//        person4.showMyPayers();

//        System.out.println(person4.balance);


        System.out.println("-----------------------------------------");
        group.simplifyGroupExpenses();
        person4.showMyPayers();
        person1.showMyPayers();
        person2.showMyPayers();
        person3.showMyPayers();
        person4.payADebt(person3,75);
        person4.showMyPayers();
        person3.showMyPayers();
        System.out.println(person3.balance);


    }
}
