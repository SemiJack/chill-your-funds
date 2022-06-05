package chillyourfunds.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    Group group;
    Person person1;
    Person person2;
    Person person3;
    EqualExpense eq1;
    @BeforeEach
    void setUp() {
        group=new Group("Szefy",2115);
        person1=new Person(1,"Eryk");
        person2=new Person(2,"Sergio");
        person3=new Person(3,"Hugo");
        eq1=new EqualExpense(100,group,person1);
        group.addPerson(person1);
        group.addPerson(person2);
        eq1.addDebtor(1);
        eq1.addDebtor(2);
        eq1.equalSplit();
    }



    @AfterEach
    void tearDown() {
        group.getPeople().clear();
    }

    @Test
    void addPerson() {
        group.addPerson(person3);
        assertEquals(person3, group.getPeople().get(2));
        assertEquals(person2, group.getPeople().get(1));
    }

    @Test
    void removePerson() {
        group.removePerson(person3);
        assertEquals(2, group.getPeople().size());
    }

    @Test
    void getPersonById() {
        assertEquals(person1,group.getPersonById(1));
    }

    @Test
    void getPersonByName() {
        assertEquals(person1,group.getPersonByName("Eryk"));
    }

    @Test
    void simplifyGroupExpenses() {
        group.simplifyGroupExpenses(group);

        assertEquals(-50,person1.getBalance(group));
        assertEquals(50,person2.getBalance(group));
        assertEquals(0,person3.getBalance(group));
    }

}