package Workshop_Socket;

import java.io.BufferedReader;
import java.io.IOException;

public class ThreadModtager extends Thread {
    private BufferedReader inFromUnit;

    public ThreadModtager(BufferedReader inFromUnit) {
        this.inFromUnit = inFromUnit;
    }
    /** The key is that the BufferedReader is already configured before it's passed in
     * — it's set up in the caller (TCPServer/TCPClient) and then just handed to ThreadModtager.
     So in TCPServer:
     javaBufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

     ThreadModtager threadModtager = new ThreadModtager(inFromClient);
     By the time ThreadModtager receives it, the BufferedReader already knows it's connected to the socket's input stream.
     ThreadModtager doesn't need to know or care about that — it just calls readLine() on whatever reader it was given.
     This is actually a nice design principle called dependency injection —
     instead of ThreadModtager creating its own reader, it just receives one ready-to-use.
     This makes ThreadModtager reusable for any BufferedReader, whether it's:

     A socket input stream
     A file
     User keyboard input (System.in)

     You can actually see this in the code — the same ThreadModtager class is reused on both client and server,
     even though one is reading from a server socket and the other from a client socket.
     It doesn't need to know the difference! */
    @Override
    public void run() {
        try {
            while (true) {
                String message = inFromUnit.readLine();
                System.out.println(message);

                if (message.equalsIgnoreCase("stop")) {
                    System.out.println("Forbindelse nedlagt");
                    this.interrupt();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
