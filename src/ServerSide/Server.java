package ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerModel model = new ServerModel();

        try {
            ServerSocket welcomeSocket = new ServerSocket(7458);
            System.out.println("Server started");
            Socket socket;

            while(true)
            {
                socket = welcomeSocket.accept();
                System.out.println("Client connected");

                ServerSocketHandler ssh = new ServerSocketHandler(
                        socket,
                        model
                );

                Thread t = new Thread(ssh);
//                t.setDaemon(true);
                t.start();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
