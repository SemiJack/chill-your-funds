package chillyourfunds.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    Group group;
    Person person1;
    Person person2;
    Person person3;
    Person person4;
    EqualExpense eq1;
    EqualExpense eq2;


    @BeforeEach
    void setUp() {
        group=new Group("Szefy",2115);
        Person person1=new Person("eric","Eryk");
        Person person2=new Person("gregor","Grzechu");
        Person person3=new Person("kris","Krzychu");
        Person person4=new Person("json","Jacek");
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);
        eq1=new EqualExpense(100,group,person1);
        eq1.addDebtor("eric");
        eq1.addDebtor("gregor");
        eq1.addDebtor("kris");
        eq1.addDebtor("json");
        eq2=new EqualExpense(300,group,person3);
        eq2.addDebtor("eric");
        eq2.addDebtor("gregor");
        eq2.addDebtor("json");
    }
    @AfterEach
    void tearDown() {
        group.people.clear();
    }

    @org.junit.jupiter.api.Test
    void addPersonTest() {
        assertEquals(4,group.people.size());
        assertEquals(group.getPersonByName("eric"),person1);
    }

    @org.junit.jupiter.api.Test
    void removePerson() {
        group.removePerson(person1);
        assertEquals(3,group.people.size());
    }

//    @org.junit.jupiter.api.Test
//    void getPersonById() {
//        assertEquals(group.getPersonById(1),person1);
//        assertEquals(group.getPersonById(2),person2);
//        assertEquals(group.getPersonById(3),person3);
//        assertEquals(group.getPersonById(4),person4);
//    }

    @org.junit.jupiter.api.Test
    void getPersonByName() {
        assertEquals(group.getPersonByName("Eryk"),person1);
        assertEquals(group.getPersonByName("Grzechu"),person2);
        assertEquals(group.getPersonByName("Krzychu"),person3);
        assertNotEquals(group.getPersonByName("Sergio"),person4);
    }

    @org.junit.jupiter.api.Test
    void equalExpenseTest() {
        eq1.createExpense(eq1);
        eq1.equalSplit();
        assertEquals(-75,person1.getBalance());
        assertEquals(25,person2.getBalance());
        assertEquals(25,person3.getBalance());
        assertEquals(25,person4.getBalance());
//        ex1.exactSplit();
    }
    @Test
    void simplifyTest(){
        eq1.equalSplit();
        eq2.createExpense(eq2);
        eq2.equalSplit();
        group.simplifyGroupExpenses();
        assertEquals(25,person1.getBalance());
        assertEquals(125,person2.getBalance());
        assertEquals(-275,person3.getBalance());
        assertEquals(125,person4.getBalance());
    }


}