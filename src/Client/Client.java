package Client;

import Classes.Message;
import Classes.Request;
import Classes.User;
import mvvm.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Model model;
    private ClientSocketHandler clientSocketHandler;

    public Client(Model model) {
        this.model = model;
        model.setClient(this);

        try{
            Socket socket = new Socket("localhost", 7458);
            clientSocketHandler = new ClientSocketHandler(
                    new ObjectOutputStream(socket.getOutputStream()),
                    new ObjectInputStream(socket.getInputStream()),
                    this);
            Thread t = new Thread(clientSocketHandler);
            t.setDaemon(true);
            t.start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void receiveFromServer(Request request)
    {
        model.receiveRequest(request);
    }

    public void sendToServer(Request request)
    {
        clientSocketHandler.sendRequestToServer(request);
    }

    public void logOut(User user) {
        clientSocketHandler.logOut(new Request(Request.TYPE.USER_LOG_OUT, user));
    }
}
