package chillyourfunds.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class LoginController {
    SceneController sceneController=new SceneController();
    CYFApplication cyfApplication=new CYFApplication();
    MainController mainController=new MainController();
    CYFClientController client=new CYFClientController("localhost","40000");
    @FXML
    TextField username=new TextField();
    @FXML
    PasswordField password=new PasswordField();
    @FXML
    Button okbutton=new Button();

    Stage stage;

    String usernameString,passwordString;

    public LoginController() throws Exception {
    }

    public void changeScene(){

    }


    public void submit(ActionEvent e) throws IOException {
        usernameString=username.getText();
        passwordString=password.getText();
        sceneController.switchToMain(e);
        client.login(usernameString,passwordString);
    }

}
