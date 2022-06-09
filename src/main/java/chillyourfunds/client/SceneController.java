package chillyourfunds.client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class SceneController {

        private Stage stage;
        private Scene scene;
        private Parent root;

        public void switchToMain(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("fxml/HomeView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToLoginScene(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToAddGroup(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/addGroupView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        public void switchToGroupView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/GroupView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        public void switchToAddExpenseView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/addExpenseView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        public void switchToEqualSplitView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/EqualExpense.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToExactSplitView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/ExactExpense.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToPercentSplitView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/PercentExpense.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToAddPersonView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/addPersonView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        public void switchToGroupListView(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Grouplist.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
