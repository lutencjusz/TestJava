import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Serializacja {

    public static void main(String[] args) {
        Demo object = new Demo(1, "geeksforgeeks");
        System.out.println("a = " + object.getA());
        System.out.println("b = " + object.getB());
        System.out.println("tajne hasło = " + object.getPass());
        String filename = "SerializedFile.txt";

        // Serialization
        try {
            //Saving of object in a file
            ObjectOutputStream out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filename)));

            // Method for serialization of object
            out.writeObject(object);

            out.close();

            System.out.println("Obiekt został zapisany w pliku '" + filename + "' jako zserializowany");


            Demo object1 = null;

            // Deserialization

            // Reading the object from a file
            ObjectInputStream in = new ObjectInputStream(new GZIPInputStream(new FileInputStream(filename)));

            // Method for deserialization of object
            object1 = (Demo) in.readObject();

            in.close();

            System.out.println("Obiekt został pobrany z pliku '" + filename + "' jako zdeserializowany");
            System.out.println("a = " + object1.getA());
            System.out.println("b = " + object1.getB());
            System.out.println("tajne hasło = " + object1.getPass());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Błąd: " + ex.getMessage());
        }

    }

}

