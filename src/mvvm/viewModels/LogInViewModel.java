package mvvm.viewModels;

import Classes.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mvvm.Model;
import mvvm.views.ViewHandler;

import java.beans.PropertyChangeEvent;

public class LogInViewModel {
    private StringProperty userName;
    private StringProperty password;
    private StringProperty invalidInput;

    private Model model;
    private ViewHandler viewHandler;

    public LogInViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;

        userName = new SimpleStringProperty();
        password = new SimpleStringProperty();
        invalidInput = new SimpleStringProperty();

        model.addListener("userApproved", this :: userApproved);
        model.addListener("userDisapproved", this :: userDisapproved);
        model.addListener("userOnline", this::userOnline);
    }

    private void userOnline(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            userName.setValue("");
            password.setValue("");
            invalidInput.setValue("User already logged on");
        });
    }

    private void userDisapproved(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
                userName.setValue("");
                password.setValue("");
                invalidInput.setValue("User name and password don't match");
        });
    }

    private void userApproved(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() -> {
            userName.setValue("");
            password.setValue("");
            invalidInput.setValue("");
            viewHandler.openView("chatView");
        });
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty invalidInputProperty() {
        return invalidInput;
    }

    public void logIn() {
        if(userName.getValue() == null || userName.getValue().equals("") ||
                password.getValue() == null || password.getValue().equals(""))
        {
            invalidInput.setValue("Invalid input");
            userName.setValue("");
            password.setValue("");
        }
        else model.userLogIn(new User(userName.getValue(), password.getValue()));
    }

    public void signUp() {
        userName.setValue("");
        password.setValue("");
        invalidInput.setValue("");
        viewHandler.openView("signUpView");
    }
}
