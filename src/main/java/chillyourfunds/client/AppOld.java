package chillyourfunds.client;

import chillyourfunds.logic.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppOld extends javafx.application.Application{

    void initialize(){
        Person person1=new Person(1,"Eryk");
        Person person2=new Person(2,"Grzechu");
        Person person3=new Person(3,"Krzychu");
        Person person4=new Person(4,"Jacek");
        Group group=new Group("szefy",1);
        group.addPerson(person1);
        group.addPerson(person2);
        group.addPerson(person3);
        group.addPerson(person4);
        EqualExpense e1 = new EqualExpense(100, group, person1);
        e1.addDebtor(1);
        e1.addDebtor(2);
        e1.addDebtor(3);
        e1.addDebtor(4);
        e1.createExpense(e1);
        e1.equalSplit();

        System.out.println("Stan konta "+person1.getName()+" wynosi "+person1.getBalance()+"$");
        System.out.println("Stan konta "+person2.getName()+" wynosi "+person2.getBalance()+"$");
        System.out.println("Stan konta "+person3.getName()+" wynosi "+person3.getBalance()+"$");
        System.out.println("Stan konta "+person4.getName()+" wynosi "+person4.getBalance()+"$");
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
