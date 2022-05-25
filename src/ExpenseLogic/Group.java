package ExpenseLogic;

import java.util.ArrayList;
import java.util.List;

public class Group{
    String groupName;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    List<Person> people=new ArrayList<>();
    List<Expense> expenses=new ArrayList<>();
    void addPerson(Person person){
        people.add(person);
    }
    void removePerson(Person person){
        people.remove(person);
    }

    public Person getPersonById(int id) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).id == id) {
                p = people.get(i);
            }
        }
        return p;
    }

    public Person getPersonByName(String name) {
        Person p = null;
        for (int i = 0; i < people.size(); i++) {
            if (people.get(i).name.equals(name)) {
                p = people.get(i);
            }
        }
        return p;
    }




    //    void addExpense(Expense expense){
//        expenses.add(expense);
//    }

//    void realizeExpense(){
//        ExpenseLogic.Person p1,p2,p3;
//    }


}