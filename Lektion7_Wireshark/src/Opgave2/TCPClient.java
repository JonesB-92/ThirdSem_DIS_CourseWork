package Opgave2;

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

		Socket clientSocket = new Socket("", 6789);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//		sentence = inFromUser.readLine();
//		outToServer.writeBytes(sentence + '\n');
//
		//Opgave 3
		//Prøv at aflevere en besked lidt af gangen. Skriv i TCPClient noget i stil med
//		outToServer.writeBytes("HaHa");
//		outToServer.writeBytes("HeHe");
//		outToServer.writeBytes("HiHi");
//		outToServer.writeBytes("HoHo\n");
		/**
		 *
		 */

//		Prøv at sætte en forsinkelse ind mellem de ovenstående writebytes, f.eks.
//		Thread.sleep(5), hvor 5 er ventetiden i millisekunder.
		outToServer.writeBytes("HaHa");
		Thread.sleep(500);
		outToServer.writeBytes("HeHe");
		Thread.sleep(500);
		outToServer.writeBytes("HiHi");
		Thread.sleep(500);
		outToServer.writeBytes("HoHo\n");

		//På Socket-klassen er der en operation setTcpNoDelay, prøv at sætte den til true og se
		//om det ændrer noget.
//		clientSocket.setTcpNoDelay(true);

//		modifiedSentence = inFromServer.readLine();
//		System.out.println("FROM SERVER: " + modifiedSentence);

		clientSocket.close();


		/**
		 Opgave 4
		 
		 */
	}

}
