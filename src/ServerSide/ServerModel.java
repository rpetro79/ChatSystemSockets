package ServerSide;

import Classes.Message;
import Classes.Request;
import Classes.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class ServerModel {
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private ArrayList<String> onlineUsers;

    public ServerModel() {
        this.onlineUsers = new ArrayList<String>();
    }

    public void newMessage(Request request)
    {
        changeSupport.firePropertyChange("newMessage", null, request);
    }

    public void userConnected(User user)
    {
        onlineUsers.add(user.getName());
        changeSupport.firePropertyChange("userConnected", null, onlineUsers);
    }

    public void userLogOut(User user)
    {
        if(user != null)
        {
            onlineUsers.remove(user.getName());
            changeSupport.firePropertyChange("userLogOut", null, onlineUsers);
        }
    }

    public void addListener(String name, PropertyChangeListener listener)
    {
        if(name == null || "".equals(name))
            changeSupport.addPropertyChangeListener(listener);
        else
            changeSupport.addPropertyChangeListener(name, listener);
    }

    public ArrayList<String> getOnlineUsers() {
        return onlineUsers;
    }
}
