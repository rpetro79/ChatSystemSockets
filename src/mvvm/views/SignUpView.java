package mvvm.views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import mvvm.viewModels.SignUpViewModel;


public class SignUpView {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField secondPasswordField;
    @FXML
    private Button createAccountButton;
    @FXML
    private Label invalidInput;
    @FXML
    private Button logInButton;

    private SignUpViewModel viewModel;

    public void init(SignUpViewModel vm)
    {
        viewModel = vm;

        userNameField.textProperty().bindBidirectional(viewModel.userNameProperty());
        passwordField.textProperty().bindBidirectional(viewModel.password1Property());
        secondPasswordField.textProperty().bindBidirectional(viewModel.password2Property());
        invalidInput.textProperty().bindBidirectional(viewModel.invalidInputProperty());

        secondPasswordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                {
                    createAccount();
                }
            }
        });
    }

    public void createAccount()
    {
        viewModel.createAccount();
    }

    public void logIn()
    {
        viewModel.backToLogIn();
    }
}
