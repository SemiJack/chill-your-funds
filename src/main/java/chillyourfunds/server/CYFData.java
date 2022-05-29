package chillyourfunds.server;

import chillyourfunds.logic.Group;
import chillyourfunds.logic.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;


public class CYFData {
    private HashMap<Integer, Group> database; //struktura danych do przechowwywania wszystkich transakcji w historii? w pliku? w arrayliście? w linkedliście? w streamie? w bazie danych??


    public CYFData(String filename) {
        try {
            readJSON(filename);
            if(database==null){
                database = new HashMap<Integer, Group>();
            }
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
            database = new HashMap<Integer,Group>();
            saveJSON(filename);
        }
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

    public void readJSON(String filepath) throws FileNotFoundException{
            Gson gson = new Gson();
            HashMap<Integer,Group> newdata = gson.fromJson(new FileReader(filepath), new HashMap<Integer, Group>() {
            }.getClass().getGenericSuperclass());
            this.database = newdata;
    }

    public void addGroup( String name){
        Integer id = new Random().nextInt();
        if(database.containsKey(id)){
            id = new Random().nextInt();
        }
        database.put(id,new Group(name));
    }

    public Group getGroup(Integer id){
        return  database.get(id);
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

    public static void main(String[] args) {
        CYFData dat =  new CYFData("dabdatase.json");

    }

}
