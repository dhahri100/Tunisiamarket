package ViewControl;

import Control.LoginControl;
import ModelLogin.Login;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class LoginFormControl {
    @FXML
    private TextField textUser;
    @FXML
    private PasswordField textpass;
    @FXML
    private Button btnLog;
    @FXML
    private Button btnsig;
    @FXML
    private Label errorMSG;
    private final LoginControl lgc = new LoginControl();

    @FXML
    public void Login(Event e) {
        String username = textUser.getText();
        String password = textpass.getText();

        try {
            if (lgc.isLogin(username,password)) {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                errorMSG.setText("Username or password is incorrect.");
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }
}
