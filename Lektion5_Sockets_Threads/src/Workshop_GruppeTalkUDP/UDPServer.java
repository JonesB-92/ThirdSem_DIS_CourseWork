package Workshop_GruppeTalkUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPServer {

    public static void main(String args[]) throws Exception {
        //Bemærk at lige nu kan man IKKE bruge broadcast på skolens net. Vi skal derfor anvende
        //vores egne trådløse router.
        //SSID :
        //Password :
        //Vi vil som portnummer anvende 7777

        DatagramSocket serverSocket = new DatagramSocket(7777);
        byte[] sendData = new byte[1024];


        while (true) {
            /** Modtagelse */
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);

            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());
            System.out.println(sentence.trim());

            /** Svar */
//            InetAddress IPAddress = receivePacket.getAddress();
//            int port = receivePacket.getPort();
//
//            String capitalizedSentence = sentence.toUpperCase();
//            sendData = capitalizedSentence.getBytes();
//
//            DatagramPacket sendPacket = new DatagramPacket(sendData,
//                    sendData.length, IPAddress, port);
//
//            serverSocket.send(sendPacket);
        }
    }
}
