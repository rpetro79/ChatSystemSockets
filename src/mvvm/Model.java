package mvvm;

import Classes.Message;
import Classes.Request;
import Classes.User;
import Client.Client;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private Client client;
    private User user;
    private PropertyChangeSupport changeSupport;

    public Model() {

        changeSupport = new PropertyChangeSupport(this);
    }

    public void addListener(String name, PropertyChangeListener listener)
    {
        if(name == null || name.equals(""))
            changeSupport.addPropertyChangeListener(listener);
        else
            changeSupport.addPropertyChangeListener(name, listener);
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void receiveRequest(Request request) {
        if(request.type == Request.TYPE.MESSAGE)
            changeSupport.firePropertyChange("newMessage", null, (Message)request.getToBeSent());
        else if(request.type == Request.TYPE.USER_APPROVED)
        {
            changeSupport.firePropertyChange("userApproved", null, null);
            this.user = (User) request.getToBeSent();
        }
        else if(request.type == Request.TYPE.USER_DISAPPROVED)
        {
            changeSupport.firePropertyChange("userDisapproved", null, null);
        }
        else if(request.type == Request.TYPE.USER_CONNECTED)
        {
            ArrayList<String> list = (ArrayList<String>) request.getToBeSent();
            changeSupport.firePropertyChange("userListModified", null, request.getToBeSent());
        }
        else if(request.type == Request.TYPE.NEW_USER_DISAPPROVED)
        {
            changeSupport.firePropertyChange("newUserDisapproved", null, null);
        }
        else if(request.type == Request.TYPE.NEW_USER_APPROVED)
        {
            changeSupport.firePropertyChange("newUserApproved", null, null);
            this.user = (User) request.getToBeSent();
        }
        else if(request.type == Request.TYPE.USER_LOG_OUT)
        {
            changeSupport.firePropertyChange("userListModified", null, request.getToBeSent());
        }
        else if(request.type == Request.TYPE.USER_ONLINE)
        {
            changeSupport.firePropertyChange("userOnline", null, request.getToBeSent());
        }
    }

    public void userLogIn(User user)
    {
        client.sendToServer(new Request(Request.TYPE.USER, user));
    }

    public void newUser(User user)
    {
        client.sendToServer(new Request(Request.TYPE.USER_NEW, user));
    }

    public void sendMessageToServer(Message message)
    {
        client.sendToServer(new Request(Request.TYPE.MESSAGE, message));
    }

    public void logOut() {
        client.logOut(user);
    }
}
