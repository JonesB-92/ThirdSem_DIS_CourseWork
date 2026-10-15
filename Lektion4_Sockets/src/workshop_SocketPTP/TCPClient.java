package workshop_SocketPTP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class TCPClient {

    public static void main(String[] args) throws Exception, IOException {
        String sentence;
        String modifiedSentence;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("", 6969);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();
        outToServer.writeBytes("poke_" + sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        if (modifiedSentence.equals("ja")) {
            System.out.println("FROM SERVER: " + modifiedSentence);
        } else {
            System.out.println("Lukker");
            clientSocket.close();
        }
        while (!clientSocket.isClosed()) {
            sentence = inFromUser.readLine();
            if (sentence.equals("stop")) {
                clientSocket.close();
            } else {
                outToServer.writeBytes(sentence + '\n');
            }
            modifiedSentence = inFromServer.readLine();
            System.out.println(modifiedSentence);

        }
    }
}
