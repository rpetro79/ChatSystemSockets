package ServerSide;

import BinaryFiles.UsersFileAdapter;
import Classes.Message;
import Classes.Request;
import Classes.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketHandler implements Runnable {
    private ObjectOutputStream sendToClient;
    private ObjectInputStream readFromClient;
    private ServerModel model;
    private ArrayList<User> users;
    private UsersFileAdapter usersFileAdapter;
    private Socket socket;
    private User myUser;
    private boolean stillOn = true;

    public ServerSocketHandler(Socket socket, ServerModel model) {
        this.model = model;
        this.socket = socket;
        try {
            this.sendToClient = new ObjectOutputStream(socket.getOutputStream());
            this.readFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        users = new ArrayList<User>();

        model.addListener("newMessage", this::newMessage);
        model.addListener("userConnected", this::userConnected);
        model.addListener("userLogOut", this::userLogOut);

        usersFileAdapter = new UsersFileAdapter();
    }

    private void userLogOut(PropertyChangeEvent propertyChangeEvent) {
        if(stillOn)
        {
            try {
                ArrayList<String> list = (ArrayList<String>) propertyChangeEvent.getNewValue();
                ArrayList<String> l = new ArrayList<String>(list);
                sendToClient.writeObject(new Request(Request.TYPE.USER_LOG_OUT, l));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void userConnected(PropertyChangeEvent propertyChangeEvent) {
        if(stillOn)
        {

            ArrayList<String> list = (ArrayList<String>) propertyChangeEvent.getNewValue();
            try {
                ArrayList<String> tmp = new ArrayList<>(list);
                Request obj = new Request(Request.TYPE.USER_CONNECTED, tmp);
                sendToClient.writeObject(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void newMessage(PropertyChangeEvent propertyChangeEvent) {
        if(stillOn)
        {
            Request request = (Request) propertyChangeEvent.getNewValue();
            try {
                sendToClient.writeObject(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void run() {
        Message message;
        Request req;
        User user;
        try {
            while (true) {
                req = (Request) readFromClient.readObject();
                if (req.type == Request.TYPE.MESSAGE) {
                    model.newMessage(req);
                } else if (req.type == Request.TYPE.USER) {
                    boolean ok = false;
                    user = (User) req.getToBeSent();
                    users = usersFileAdapter.readUserList();
                    for (int i = 0; i < users.size(); ++i) {
                        if (user.equals(users.get(i)))
                        {
                            ok = true;
                            break;
                        }
                    }
//                    if (i != users.size()) {
//                        myUser = user;
//                        model.userConnected(user);
//                        sendToClient.writeObject(new Request(Request.TYPE.USER_APPROVED, user));
//                    }
                    if(ok == false)
                        sendToClient.writeObject(new Request(Request.TYPE.USER_DISAPPROVED, user));
                    else {
                        ArrayList<String> online = model.getOnlineUsers();
                        int j = 0;
                        System.out.println(online);
                        for(j = 0; j < online.size(); ++j)
                        {
                            if(user.getName().equals(online.get(j)))
                            {
                                sendToClient.writeObject(new Request(Request.TYPE.USER_ONLINE, user));
                                break;
                            }
                        }
                        if(j == online.size())
                        {
                            myUser = user;
                            model.userConnected(user);
                            sendToClient.writeObject(new Request(Request.TYPE.USER_APPROVED, user));
                            System.out.println("user approved");
                        }

                    }
                } else if (req.type == Request.TYPE.USER_NEW) {
                    user = (User) req.getToBeSent();
                    users = usersFileAdapter.readUserList();
                    int i = 0;
                    for (i = 0; i < users.size(); ++i) {
                        if (users.get(i).getName().equals(user.getName())) {
                            sendToClient.writeObject(new Request(Request.TYPE.NEW_USER_DISAPPROVED, user));
                            break;
                        }
                    }
                    if (i == users.size()) {
                        model.userConnected(user);
                        users.add((User) req.getToBeSent());
                        usersFileAdapter.saveUserList(users);
                        myUser = user;
                        sendToClient.writeObject(new Request(Request.TYPE.NEW_USER_APPROVED, user));
                    }
                } else if (req.type == Request.TYPE.USER_LOG_OUT) {
                    model.userLogOut((User) req.getToBeSent());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            stillOn = false;
            try {
                readFromClient.close();
                sendToClient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            model.userLogOut(myUser);
//            e.printStackTrace();
        }
    }
}
