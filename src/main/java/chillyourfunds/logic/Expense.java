package chillyourfunds.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa Expense reprezentuje wydatek odbywający się wewnątrz grupy.
 * Przyjmuje ona parametry takie jak wartość wydatku, grupa, w ramach
 * której wydatek jest realizowany, osobę, która poniosła koszt, wartość tego,
 * czy dług został uregulowany, a także listę dłużników. Implementuje ona metody takie jak:
 * addDebtor, createExpense, isPayerADebtor i toString. Po tej klasie dziedziczą klasy EqualExpense i ExactExpense.
 */
public class Expense {
    int amount;
    Group group;
    Person payer;
    boolean isPaid = false;
    List<Person> debtors = new ArrayList<>();

    /**
     * @param amount wartość poniesionego kosztu
     * @param group grupa, na którą przypisany jest wydatek
     * @param payer osoba, która faktycznie zapłaciła
     */
    public Expense(int amount, Group group, Person payer) {
        this.amount = amount;
        this.group = group;
        this.payer = payer;
    }

    /**
     * Metoda addDebtor dodaje użytkownika do listy dłużników.
     * @param id numer identyfikujący osobę będącą dłużnikiem
     */
    public void addDebtor(int id) {
        if(group.getPersonById(id) != null) {
            debtors.add(group.getPersonById(id));
        } else {
            System.out.println("Nie można dodać osoby spoza grupy jako dłużnika!");
        }

    }

    /**
     * Metoda createExpense dodaje wydatek i przypisuje go do grupy
     * @param e wydatek, który jest realizowany
     */
    public void createExpense(Expense e) {
        group.getExpenses().add(e);
    }

    /**
     * Metoda isPayerADebtor
     * @return zwraca wartość true dla osoby która jest płatnikiem
     */
    public boolean isPayerADebtor() {
        for (int i = 0; i < debtors.size(); i++) {
            if(debtors.get(i).equals(payer)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Expense{" +
                "amount=" + amount +
                ", groupName=" + group.getGroupName() +
                ", payer=" + payer +
                ", debtors=" + debtors +
                '}';
    }
}
