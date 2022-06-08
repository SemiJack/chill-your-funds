package chillyourfunds.logic;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class LogicTest {
    public static void main(String[] args) throws WrongPercentException, WrongAmountException {
        Person person1=new Person(1,"Eryk");
        Person person2=new Person(2,"Grzechu");
        Person person3=new Person(3,"Krzychu");
        Person person4=new Person(4,"Jacek");
        Person person5 = new Person(5,"Sergio");
        Group group=new Group("szefy",997);
        Group group1 = new Group("essa",2115);
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);
        group1.addPerson(person1);
        group1.addPerson(person3);
        group1.addPerson(person5);

//        EqualExpense e1 = new EqualExpense(12, group, person1);
//        EqualExpense e2 = new EqualExpense(12, group, person2);
        EqualExpense e3 = new EqualExpense(30, group, person2);
        ExactExpense e4 = new ExactExpense(5000,group1,person5);
//        PercentExpense e5 = new PercentExpense(100,group1,person5);
//        e1.addDebtor(3);
//        e1.createExpense(e1);
//        System.out.println("------------------");
//        e2.addDebtor(1);
//        e2.createExpense(e2);
//        e3.addDebtor(1);
//        e3.addDebtor(3);
//        e3.addDebtor(4);
//        e3.createExpense(e3);
//        e1.equalSplit();
//        e2.equalSplit();
        //e3.equalSplit();
        e4.addDebtor(5);
        e4.addDebtor(1);
        e4.createExpense(e4);
        Map<Integer,Integer> map = new HashMap<>();
        map.put(5,2500);
        map.put(1,2500);

        try{
            e4.exactSplit(map);
        } catch (Exception e) {
            System.out.println("Nastąpił problem: " + e);
            System.out.println("Spróbuj ponownie: ");
            e4.exactSplit(map);
        }
//
//        e5.addDebtor(1);
//        e5.addDebtor(3);
//        e5.createExpense(e5);
//        try{
//            e5.percentSplit();
//        } catch (Exception e) {
//            System.out.println("Nastąpił problem: " + e);
//            System.out.println("Spróbuj ponownie: ");
//            e5.percentSplit();
//        }

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
//        System.out.println("Stan konta "+person1.name+" wynosi "+person1.balance+"$");
//        System.out.println("Stan konta "+person2.name+" wynosi "+person2.balance+"$");
//        System.out.println("Stan konta "+person3.name+" wynosi "+person3.balance+"$");
//        System.out.println("Stan konta "+person4.name+" wynosi "+person4.balance+"$");
//        System.out.println();
//        System.out.println(person2.name+" jest winien "+person1.name+" "+person2.mapOfExpenses.get(person1)+"$");
//        System.out.println(person2.name+" jest winien "+person4.name+" "+person2.mapOfExpenses.get(person4)+"$");
////
//        person1.showMyPayers();
//        person2.showMyPayers();
//        person3.showMyPayers();
//        person4.showMyPayers();

//       person4.payADebt(person1,25);
//
//        person4.showMyPayers();

//        System.out.println(person4.balance);


//        System.out.println("-----------------------------------------");
//        person4.showMyPayers();
//        person1.showMyPayers();
//        person2.showMyPayers();
//        person3.showMyPayers();
//        person4.payADebt(person3,75);
//        person4.showMyPayers();
//        person3.showMyPayers();
//        System.out.println(person3.balance);


        System.out.println("---------------------------");
//        System.out.println(person1.mapOfBalances);

        person1.showMyPayers();
        person2.showMyPayers();
        person3.showMyPayers();
        person4.showMyPayers();
        person5.showMyPayers();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        //group.simplifyGroupExpenses(group);
        System.out.println();
        System.out.println();
        System.out.println();
        person1.showMyPayers();
        person2.showMyPayers();
        person3.showMyPayers();
        person4.showMyPayers();
        person5.showMyPayers();
        System.out.println(person1.getBalance(group));
        //group1.simplifyGroupExpenses(group1);
        //person3.payADebt(person2,group,12);
        //person3.showMyPayers();
        //person3.showMyBalances();
        //person5.showMyBalances();
        //person5.showMyPayers();


    }
}
