package ExpenseLogic;

import java.util.ArrayList;
import java.util.List;

public class Group{
    List<Person> people=new ArrayList<>();
    List<Expense> expenses=new ArrayList<>();
    void addPerson(Person person){
        people.add(person);
    }
    void removePerson(Person person){
        people.remove(person);
    }

//    void addExpense(Expense expense){
//        expenses.add(expense);
//    }

//    void realizeExpense(){
//        Person p1,p2,p3;
//    }


}
