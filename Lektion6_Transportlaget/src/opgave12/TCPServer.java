package opgave12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer {
    // lav tom List og smid Personbjekter ind
    public static void main(String[] args) throws Exception, IOException {
        ArrayList<Person> personer = new ArrayList<>();

        ServerSocket welcomSocket = new ServerSocket(6789);

        while (true) {
            Socket connectionSocket = welcomSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            modtagPersoner(personer, inFromClient);

            System.out.println(personer);
        }
    }

    //ModtagPersoner (ArrayList af personer a, BufferedReader instream)
    //Lytter efter en besked på instream og udpakker beskeden til ArrayListen a. (Hint: split). Denne
    //kaldes fra et server-program
    public static void modtagPersoner(ArrayList<Person> personer, BufferedReader inStream) throws IOException {
        String personerSomString = inStream.readLine();

        String[] personerSomStringArr = personerSomString.split("#");

        for (int i = 0; i < personerSomStringArr.length - 2; i += 3) {
            int id = Integer.parseInt(personerSomStringArr[i]);
            String navn = personerSomStringArr[i + 1];
            String by = personerSomStringArr[i + 2];
            personer.add(new Person(id, navn, by));
        }
//        System.out.println(personer.toString());
    }
}

