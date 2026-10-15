package opgave12;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPClient {
    //Lav objekt herinde

    public static void main(String[] args) throws Exception, IOException {
        Person person1 = new Person(1, "Saed", "Aarhus");
        Person person2 = new Person(2, "Jones", "BXL");
        Person person3 = new Person(3, "Arthur", "Warszawa");

        ArrayList<Person> personer = new ArrayList<>(List.of(person1, person2, person3));

        Socket clientSocket = new Socket("localhost", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        SendPersoner(personer, outToServer);

        clientSocket.close();
    }

    //Metoden SendPersoner (ArrayList af personer a, DataOutputStream outstream):
    //Pakker a ned i en String, der sendes på outstream. Denne kaldes fra et client-program
    public static void SendPersoner(ArrayList<Person> personer, DataOutputStream outStream) throws IOException {
        String personerToString = "";
        for (Person person : personer) {
            personerToString += person.getId() + "#" + person.getNavn() + "#" + person.getBy() + "#";
        }
        System.out.println(personerToString);
        outStream.writeBytes(personerToString);
    }
}
