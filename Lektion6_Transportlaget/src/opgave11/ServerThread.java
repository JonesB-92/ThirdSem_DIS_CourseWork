package opgave11;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    Socket connSocket;
    common c;

    public ServerThread(Socket connSocket, common c) {
        this.connSocket = connSocket;
        this.c = c; // Til Web-server opgaven skal denne ikke anvendes
    }

    /**
     * Du skal nu i gang med at lave WEB-serveren. Vi laver en meget forsimplet WEB-server, idet der
     gælder følgende:
     • WEB-serveren kan kun håndtere GET-requests, URL-encodede data må ikke
     medsendes.
     • WEB-serveren læser kun første headerlinie – alle efterfølgende linier læses ikke.
     • Dokumentnavn angives med extension – kun nogle få extensions er tilladte
     Selve requestet fra din browser består altså af et antal headerlinier, hvoraf din WEB-server kun læser
     den første. Den første linje vil have formen
     GET /test.html HTTP/1.1
     */
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connSocket.getOutputStream());

            // Do the work and the communication with the client here
            // The following two lines are only an example

            String clientSentence = inFromClient.readLine();
            System.out.println(clientSentence);

            /** OPGAVE 11
             * Nu skal du programmere serveren, så den sender de nødvendige headerlinjer + det indhold browseren
             * har bedt om (i dette tilfælde test2.html) tilbage til browseren.

             * Selve requestet fra din browser består altså af et antal headerlinier, hvoraf din WEB-server kun læser
             * den første. Den første linje vil have formen
             * GET /test.html HTTP/1.1

             */
            // Deler første linje op og tager fat i test.html (jf. 6. HTTPforklaring)
            String[] beskeder = clientSentence.split(" ");
            String dokumentNavn = beskeder[1];
            /** I forbindelse med svaret tilbage til browseren er WEB-serveren nødt til at sende et antal headerlinjer
             for at browseren forstår tingene korrekt. Følgende linjer er nødvendige.
             1. HTTP 200 OK
             2. Content-Type: xxxx <-- xxxx er den aktuelle content-Type
             3. Connection: Close
             4. <-- husk den tomme linje til at afslutte header-delen
             5. Selve indholdet af dokumentet*/
            //* Nu skal du programmere serveren, så den sender de nødvendige headerlinjer + HEADERE
            // 1.
            outToClient.writeBytes("HTTP 200 OK\n");
            // 2.
            outToClient.writeBytes(ContentType(dokumentNavn) + "\n");
            // 3. + (4. = ekstra \n)
            outToClient.writeBytes("Connection: Closed\n\n");

            // det indhold browseren har bedt om (i dette tilfælde test2.html) tilbage til browseren.
            //5.
            outToClient.write(read("C:\\Users\\x\\OneDrive\\Datamatiker\\3. Semester\\Distribution, integration og sikkerhed\\Lektion-Opgaver\\Kode til opgaver\\MyWeb" + dokumentNavn));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoden ContentType modtager et filnavn og ud fra filnavnets extension
     * returneres den korrekte contenttype.
     * @return String
     */
    String ContentType(String docuname) {
        //returns the Content-Type headerline for a given document name
        if (docuname.endsWith(".html")) {
            return ("Content-Type: text/html");
        } else if (docuname.endsWith(".gif")) {
            return ("Content-Type: image/gif");
        } else if (docuname.endsWith(".png")) {
            return ("Content-Type: image/png");
        } else if (docuname.endsWith(".jpg")) {
            return ("Content-Type: image/jpg");
        } else if (docuname.endsWith(".js")) {
            return ("Content-Type: text/javascript");
        } else if (docuname.endsWith(".css")) {
            return ("Content-Type: text/css");
        } else {
            return ("Content-Type: text/plain");
        }
    }

    /**
     * Modtager et filnavn (inkl. sti) og returnerer indholdet af filen som
     * et bytearray. Når du skal skrive det ud på din outputStream skal du anvende
     * metoden write i stedet for writeBytes.
     * @param aInputFileName
     * @return byte[]
     * @throws FileNotFoundException
     */
    byte[] read(String aInputFileName) throws FileNotFoundException {
        // returns the content of a file in a binary array
        System.out.println("Reading in binary file named : " +
                aInputFileName);
        File file = new File(aInputFileName);
        System.out.println("File size: " + file.length());
        byte[] result = new byte[(int) file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while (totalBytesRead < result.length) {
                    int bytesRemaining = result.length - totalBytesRead;
                    int bytesRead = input.read(result, totalBytesRead,
                            bytesRemaining);
                    //input.read() returns -1, 0, or more :
                    if (bytesRead > 0) {
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }
                System.out.println("Num bytes read: " + totalBytesRead);
            } finally {
                System.out.println("Closing input stream.");
                input.close();
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
