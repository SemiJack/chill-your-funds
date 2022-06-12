package chillyourfunds.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    Group group;
    Person person1;
    Person person2;
    Person person3;
    EqualExpense eq1;
    ExactExpense ex1;
    PercentExpense px1;
    @BeforeEach
    void setUp() {
        group=new Group("Szefy",2115);
        person1=new Person(1,"Eryk");
        person2=new Person(2,"Sergio");
        person3=new Person(3,"Hugo");
        group.addPerson(person1);
        group.addPerson(person2);

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
    void equalTest() {
        eq1=new EqualExpense(100,group,person1);
        eq1.addDebtor(1);
        eq1.addDebtor(2);
        eq1.equalSplit();
        group.simplifyGroupExpenses(group);
        assertEquals(-50,person1.getBalance(group));
        assertEquals(50,person2.getBalance(group));
        assertEquals(0,person3.getBalance(group));
    }

    @Test
    void exactTest() throws WrongAmountException {
        group.addPerson(person3);
        ex1=new ExactExpense(100,group,person3);
        ex1.addDebtor(1);
        ex1.addDebtor(2);
        Map<Integer,Integer> map=new HashMap<>();
        map.put(1,40);
        map.put(2,60);
        ex1.exactSplit(map);
        assertEquals(40,person1.getBalance(group));
        assertEquals(60,person2.getBalance(group));
        assertEquals(-100,person3.getBalance(group));

    }

    @Test
    void percentTest() throws WrongPercentException {
        group.addPerson(person3);
        px1=new PercentExpense(200,group,person3);
        px1.addDebtor(2);
        px1.addDebtor(3);
        Map<Integer,Integer> map=new HashMap<>();
        map.put(2,40);
        map.put(3,60);
        px1.percentSplit(map);
        assertEquals(0,person1.getBalance(group));
        assertEquals(80,person2.getBalance(group));
        assertEquals(-80,person3.getBalance(group));
    }

    @Test
    void simplifyTest() throws WrongPercentException, WrongAmountException {
        group.addPerson(person3);
        px1=new PercentExpense(200,group,person3);
        px1.addDebtor(2);
        px1.addDebtor(3);
        Map<Integer,Integer> map=new HashMap<>();
        map.put(2,40);
        map.put(3,60);
        px1.percentSplit(map);
        eq1=new EqualExpense(100,group,person1);
        eq1.addDebtor(1);
        eq1.addDebtor(2);
        eq1.equalSplit();
        group.simplifyGroupExpenses(group);
        assertEquals(-50,person1.getBalance(group));
        assertEquals(130,person2.getBalance(group));
        assertEquals(-80,person3.getBalance(group));
    }
}