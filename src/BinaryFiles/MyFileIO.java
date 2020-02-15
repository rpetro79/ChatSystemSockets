package BinaryFiles;

import java.io.*;

public class MyFileIO {

    public void writeToFile(String fileName, Object obj) throws FileNotFoundException, IOException
    {
        ObjectOutputStream writeToFile = null;

        try
        {
            FileOutputStream fileOutStream = new FileOutputStream(fileName);
            writeToFile = new ObjectOutputStream(fileOutStream);

            writeToFile.writeObject(obj);
        }
        finally
        {
            if (writeToFile != null)
            {
                try
                {
                    writeToFile.close();
                }
                catch (IOException e)
                {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }
    }

    public Object readObjectFromFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException
    {
        Object obj = null;
        ObjectInputStream readFromFile = null;
        try
        {
            FileInputStream fileInStream = new FileInputStream(fileName);
            readFromFile = new ObjectInputStream(fileInStream);
            try
            {
                obj = readFromFile.readObject();
            }
            catch (EOFException eof)
            {
                //Done reading
            }
        }
        finally
        {
            if (readFromFile != null)
            {
                try
                {
                    readFromFile.close();
                }
                catch (IOException e)
                {
                    System.out.println("IO Error closing file " + fileName);
                }
            }
        }
        return obj;
    }
}
