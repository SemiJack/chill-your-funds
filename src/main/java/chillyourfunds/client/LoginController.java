package chillyourfunds.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.awt.*;

public class LoginController {
    CYFClientController client=new CYFClientController("localhost","40000");
    @FXML
    TextField username=new TextField();
    @FXML
    PasswordField password=new PasswordField();
    @FXML
    Button okbutton=new Button();



    String usernameString,passwordString;

    public LoginController() throws Exception {
    }

    public void submit(ActionEvent e){
        usernameString=username.getText();
        passwordString=password.getText();
        client.login(usernameString,passwordString);
    }

}
