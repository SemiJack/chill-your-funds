package chillyourfunds.logic;

public class LogicTest {
    public static void main(String[] args) {
        Person person1=new Person(1,"Eryk");
        Person person2=new Person(2,"Grzechu");
        Person person3=new Person(3,"Krzychu");
        Person person4=new Person(4,"Jacek");
//        Service service=new Service();
        Group group=new Group("szefy",997);
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);

        EqualExpense e1 = new EqualExpense(100, group, person1);
        EqualExpense e2 = new EqualExpense(150, group, person2);
        EqualExpense e3 = new EqualExpense(200, group, person3);
        e1.addDebtor(1);
        e1.addDebtor(2);
        e1.addDebtor(3);
        e1.addDebtor(4);
        e1.createExpense(e1);
        System.out.println("asas");
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
        System.out.println("Stan konta "+person1.name+" wynosi "+person1.balance+"$");
        System.out.println("Stan konta "+person2.name+" wynosi "+person2.balance+"$");
        System.out.println("Stan konta "+person3.name+" wynosi "+person3.balance+"$");
        System.out.println("Stan konta "+person4.name+" wynosi "+person4.balance+"$");
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
