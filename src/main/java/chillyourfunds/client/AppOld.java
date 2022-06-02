package chillyourfunds.client;

import chillyourfunds.logic.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppOld extends javafx.application.Application{

    void initialize(){
        Person person1=new Person("eric","Eryk");
        Person person2=new Person("gregor","Grzechu");
        Person person3=new Person("kris","Krzychu");
        Person person4=new Person("json","Jacek");
        Service service=new Service();
        Group group=new Group("szefy",1);
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);
        EqualExpense e1 = new EqualExpense(100, group, person1);
        e1.addDebtor("eric");
        e1.addDebtor("gregor");
        e1.addDebtor("kris");
        e1.addDebtor("json");
        e1.createExpense(e1);
        e1.equalSplit();

        System.out.println("Stan konta "+person1.getFirstname()+" wynosi "+person1.getBalance()+"$");
        System.out.println("Stan konta "+person2.getFirstname()+" wynosi "+person2.getBalance()+"$");
        System.out.println("Stan konta "+person3.getFirstname()+" wynosi "+person3.getBalance()+"$");
        System.out.println("Stan konta "+person4.getFirstname()+" wynosi "+person4.getBalance()+"$");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
