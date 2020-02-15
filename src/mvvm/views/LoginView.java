package mvvm.views;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mvvm.viewModels.LogInViewModel;

import javax.swing.text.html.ImageView;

public class LoginView {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label invalidInput;
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private ImageView image;

    private LogInViewModel viewModel;

    public void init(LogInViewModel vm)
    {
        viewModel = vm;

        userNameField.textProperty().bindBidirectional(viewModel.userNameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
        invalidInput.textProperty().bindBidirectional(viewModel.invalidInputProperty());

        passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    logInButtonPressed();
                }
            }
        });
    }

    public void logInButtonPressed()
    {
        viewModel.logIn();
    }

    public void signUpButtonPressed()
    {
        viewModel.signUp();
    }
}
