package Workshop_Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class ThreadSender extends Thread {
    private BufferedReader inFromUser;
    private DataOutputStream outToServer;

    public ThreadSender(BufferedReader inFromUser, DataOutputStream outToServer) {
        this.inFromUser = inFromUser;
        this.outToServer = outToServer;
    }

    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = inFromUser.readLine();

                outToServer.writeBytes(message + '\n');
                outToServer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
