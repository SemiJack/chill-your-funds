package chillyourfunds.server;

import chillyourfunds.logic.Group;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class CYFData {
    protected HashMap<Integer, Group> database; //struktura danych do przechowwywania wszystkich transakcji w historii? w pliku? w arrayliście? w linkedliście? w streamie? w bazie danych??


    public CYFData(String filename) {
        readJSON(filename);
    }



    public void saveJSON(String filename) {

        GsonBuilder gbuilder = new GsonBuilder();
        gbuilder.setPrettyPrinting();
        gbuilder.disableHtmlEscaping(); // for disable auto replacing special characters
        Gson gson = gbuilder.create();
        try (FileWriter pw = new FileWriter(filename)) {
            gson.toJson(database, pw);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void readJSON(String filepath) {
        try {
            Gson gson = new Gson();
            HashMap<Integer,Group> newdata = gson.fromJson(new FileReader(filepath), new HashMap<Integer, Group>() {
            }.getClass().getGenericSuperclass());
           // this.data = newdata;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
//
//    public static void main(String[] args) {
//        Group gr = new Group("dddsd");
//        HashMap<Integer, LinkedList<Expense>> database = new HashMap<>();
//        Person payer = new Person(32323, "sasas@gmail.com","jacek");
//        Person payer2 = new Person(32523, "sasas@gmyl.com","kolega");
//        LinkedList<Expense> koo = new LinkedList<>();
//        koo.add(new Expense(233,gr,payer2));
//        database.put(12,koo);
//
//        database.get(12).add(new Expense(3453,gr,payer));
//
//        System.out.println();
//
//        System.out.println(database.get(12));
//    }

}
