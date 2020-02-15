package mvvm.viewModels;

import Classes.Message;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import mvvm.Model;
import mvvm.views.ViewHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class ChatViewModel {
    private StringProperty message;
    private StringProperty messageReceived;
    private Model model;
    private ViewHandler viewHandler;
    private StringProperty userName;
    private ObservableList<String> userList;
    private PropertyChangeSupport changeSupport;

    public ChatViewModel(Model model, ViewHandler viewHandler) {
        this.model = model;
        userName = new SimpleStringProperty();
        userName.setValue("");
        message = new SimpleStringProperty();
        messageReceived = new SimpleStringProperty();
        changeSupport = new PropertyChangeSupport(this);
        model.addListener("newMessage", this :: gotNewMessage);
        model.addListener("userListModified", this :: userListModified);
        userList = FXCollections.observableArrayList();

        this.viewHandler = viewHandler;
    }

    public void addListener(String name, PropertyChangeListener listener)
    {
        if(name == null || name.equals(""))
            changeSupport.addPropertyChangeListener(listener);
        else
            changeSupport.addPropertyChangeListener(name, listener);
    }

    public ObservableList<String> getUserList() {
        return userList;
    }

    private void userListModified(PropertyChangeEvent propertyChangeEvent) {
        ArrayList<String> list = (ArrayList<String>) propertyChangeEvent.getNewValue();
        Platform.runLater(() ->
                userList.setAll((List)propertyChangeEvent.getNewValue())
                );
        changeSupport.firePropertyChange("listUpdate", null, userList);
    }

    private void gotNewMessage(PropertyChangeEvent propertyChangeEvent) {
        Message message_ = (Message)propertyChangeEvent.getNewValue();
        String str = message_.getText();
        userName.setValue(message_.getUserName());
        if(message_.getUserName().equals(model.getUser().getName()))
        {
            message.setValue("");
            messageReceived.setValue("1" + str);
        }
        else
        {
            messageReceived.setValue("2" + str);
        }
        changeSupport.firePropertyChange("newMessage", null, messageReceived.getValue());
    }

    public void send() {
        if(message.getValue() != null && !(message.getValue().equals("")))
            model.sendMessageToServer(new Message(model.getUser().getName(), message.getValue()));
    }

    public void logOut() {
        model.logOut();
        viewHandler.openView("logInView");
    }

    public StringProperty messageReceivedProperty() {
        return messageReceived;
    }

    public StringProperty messageProperty() {
        return message;
    }

    public StringProperty userNameProperty() {
        return userName;
    }
}
