package chillyourfunds.ExpenseLogic;

import java.util.ArrayList;
import java.util.List;

public class Group {
    String id,groupType,name,description,password;
    int numberOfPeople;
    boolean simplify;

    List expenses=new ArrayList<Expense>();
    List people=new ArrayList<Person>();

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void addPerson(){

    }
    public void removePerson(Person person){

    }
    public void addExpense(Expense expense){

    }

    boolean personExists(Person person){
        return true;
    }

    public Object getPerson(int index){
        return people.get(index);
    }
}
