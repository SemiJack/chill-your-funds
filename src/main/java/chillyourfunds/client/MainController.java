package chillyourfunds.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private CYFClientController client;

    @FXML
    public TabPane tabpane;
    @FXML
    public Tab tab2;

    @FXML
    private GroupViewController groupViewController;
    @FXML
    private HistoryViewController historyViewController;
    @FXML
    private HomeViewController homeViewController;

    @FXML
    public void switchToHistory(ActionEvent event) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("view/HistoryView.fxml"));
        tabpane.getSelectionModel().selectNext();
        System.out.println("dsds");
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();

    }

    public void initData(CYFClientController clientcontroller){
        this.client = clientcontroller;
    }

    public void switchToGroup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/GroupView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/HomeView.fxml"));
        root = loader.load();
        homeViewController = loader.getController();
        homeViewController.initData(client);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
