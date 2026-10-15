package Workshop_GruppeTalkUDP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {
	public static void main(String args[]) throws Exception {
		//Bemærk at lige nu kan man IKKE bruge broadcast på skolens net. Vi skal derfor anvende
		//vores egne trådløse router.
		//SSID :
		//Password :
		//Vi vil som portnummer anvende 7777
		//255.255.255.255 kan broadcaste til hele lokalnettet. Modsat TCP som KUN kan sende eller kommunikere mellem 2
		//units, kan UDP snakke med flere på samme tid (alle lytter, en slags gruppechat)

		/** Sender */
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
		clientSocket.setBroadcast(true);

		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];

		while (true){
		String sentence = inFromUser.readLine();
		sendData = sentence.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, 7777);
		clientSocket.send(sendPacket);

		}

		/** Modtager */
//		DatagramPacket receivePacket = new DatagramPacket(receiveData,
//				receiveData.length);
//		clientSocket.receive(receivePacket);
//
//		String modifiedSentence = new String(receivePacket.getData());
//		System.out.println("FROM SERVER:" + modifiedSentence.trim());
//		clientSocket.close();
	}
}
