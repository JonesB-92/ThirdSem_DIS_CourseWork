package workshop_SocketPTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        String clientSentence;
        String sentence;
        ServerSocket welcomeSocket = new ServerSocket(6969);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();
            System.out.println(clientSentence);
            System.out.println("Skriv 'ja' eller 'nej'");

            sentence = inFromUser.readLine();
            outToClient.writeBytes(sentence + '\n');

            while (true) {
                clientSentence = inFromClient.readLine();
                System.out.println(clientSentence);
                sentence = inFromUser.readLine();
                outToClient.writeBytes(sentence + '\n');
            }
        }
    }

}
