package Workshop_Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {
        String sentence;
        String modifiedSentence;

        Socket clientSocket = new Socket("localhost", 6969);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();

        outToServer.writeBytes("poke_" + sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        if (modifiedSentence.equalsIgnoreCase("ja")) {

            ThreadModtager threadModtager = new ThreadModtager(inFromServer);
            ThreadSender threadSender = new ThreadSender(inFromUser, outToServer);

            threadModtager.start();
            threadSender.start();

            threadModtager.join();
            threadSender.join();

        } else {
            System.out.println("Lukker");
            clientSocket.close();
        }

    }
}
