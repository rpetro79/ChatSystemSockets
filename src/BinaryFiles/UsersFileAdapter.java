package BinaryFiles;

import Classes.User;

import java.io.IOException;
import java.util.ArrayList;

public class UsersFileAdapter {
    MyFileIO file;

    public UsersFileAdapter() {
        file = new MyFileIO();
    }

    public void saveUserList(ArrayList<User> users)
    {
        try
        {
            file.writeToFile("users.bin", users);
        }
        catch (IOException e)
        {
            System.out.println("Problem with writing in the file");
        }
    }

    public ArrayList<User> readUserList()
    {
        ArrayList<User> users = null;
        try
        {
            users=(ArrayList<User>) file.readObjectFromFile("users.bin");
        }
        catch (ClassNotFoundException | IOException e)
        {
            System.out.println("Problem with reading the file users.bin");
            return new ArrayList<User>();
        }
        return users;
    }
}
