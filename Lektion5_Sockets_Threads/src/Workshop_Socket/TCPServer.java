package Workshop_Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        String clientSentence;
        ServerSocket welcomeSocket = new ServerSocket(6969);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println(clientSentence);
            System.out.println("Skriv 'ja' eller 'nej'");

            ThreadModtager threadModtager = new ThreadModtager(inFromClient);
            ThreadSender threadSender = new ThreadSender(inFromUser, outToClient);

            threadModtager.start();
            threadSender.start();

            threadModtager.join();
            threadSender.join();

        }
    }
}
