package Client;

import Classes.Message;
import Classes.Request;
import javafx.application.Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ClientSocketHandler implements Runnable {
    private ObjectOutputStream sendToServer;
    private ObjectInputStream readFromServer;
    private Client client;

    public ClientSocketHandler(ObjectOutputStream sendToServer, ObjectInputStream readFromServer, Client client) {
        this.client = client;
        this.sendToServer = sendToServer;
        this.readFromServer = readFromServer;
    }

    public void sendRequestToServer(Request request){
        try {
            sendToServer.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        Request received;
        while(true)
        {
            try {
                received = (Request)readFromServer.readObject();
                client.receiveFromServer(received);
            }
            catch(IOException |ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void logOut(Request request) {
        try {
            sendToServer.writeObject(request);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
