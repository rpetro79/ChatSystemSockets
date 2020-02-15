package mvvm.viewModels;

import Classes.User;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import mvvm.Model;
import mvvm.views.ViewHandler;

import java.beans.PropertyChangeEvent;

public class SignUpViewModel {
    private StringProperty userName;
    private StringProperty password1;
    private StringProperty password2;
    private StringProperty invalidInput;

    private Model model;
    private ViewHandler viewHandler;

    public SignUpViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        this.viewHandler = viewHandler;

        userName = new SimpleStringProperty();
        password1 = new SimpleStringProperty();
        password2 = new SimpleStringProperty();
        invalidInput = new SimpleStringProperty();

        model.addListener("newUserDisapproved", this :: userDisapproved);
        model.addListener("newUserApproved", this :: userApproved);
    }

    private void userApproved(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
                    invalidInput.setValue("");
                    userName.setValue("");
                    password1.setValue("");
                    password2.setValue("");
                    viewHandler.openView("chatView");
                });

    }

    private void userDisapproved(PropertyChangeEvent propertyChangeEvent) {
        Platform.runLater(() ->{
            invalidInput.setValue("User name taken");
            userName.setValue("");
            password1.setValue("");
            password2.setValue("");
        });
    }

    public void createAccount()
    {
        if(password2.getValue() == null || password2.getValue().equals("")
        || password1.getValue() == null || password1.getValue().equals("")
        || userName.getValue() == null || userName.getValue().equals(""))
        {
            userName.setValue("");
            password1.setValue("");
            password2.setValue("");
            invalidInput.setValue("Invalid input");
        }
        else if(!(password1.getValue().equals(password2.getValue())))
        {
            invalidInput.setValue("Passwords don't match");
            userName.setValue("");
            password1.setValue("");
            password2.setValue("");
        }
        else if(userName.getValue().length() > 30)
        {
            userName.setValue("");
            password1.setValue("");
            password2.setValue("");
            invalidInput.setValue("Choose a shorter user name");
        }
        else {
            model.newUser(new User(userName.getValue(), password1.getValue()));

        }
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public StringProperty password1Property() {
        return password1;
    }

    public StringProperty password2Property() {
        return password2;
    }

    public StringProperty invalidInputProperty() {
        return invalidInput;
    }

    public void backToLogIn() {
        invalidInput.setValue("");
        userName.setValue("");
        password1.setValue("");
        password2.setValue("");
        viewHandler.openView("logInView");
    }
}
