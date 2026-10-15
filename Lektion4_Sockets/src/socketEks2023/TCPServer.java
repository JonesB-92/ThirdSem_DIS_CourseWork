package socketEks2023;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomSocket = new ServerSocket(6789);
		
		while(true){
			Socket connectionSocket = welcomSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			//I øjeblikket er det client’en der nedlægger forbindelsen. Prøv at rette programmet, så serveren
			//nedlægger forbindelsen i stedet for at svare (dvs lige før den kalder writeBytes). Hvordan opdager
			//clienten det? Client giver en null
//			connectionSocket.close();

			//På Socket-klassen er der en metode setSoTimeout, hvad gør den? Prøv at anvende den i
			//programmet. Udkommenter din anvendelse igen.
//			connectionSocket.setSoTimeout(2000); // Sætter serveren til at lukke efter x antal milisek og kaster en fejl på client side
			clientSentence = inFromClient.readLine();
			capitalizedSentence = clientSentence.toUpperCase();
			//Ret server-programmet, så det hver gang en klient logger på, laver en udskrift af forskellige
			//informationer om klienten (f.eks. IP-adresse).
			String IPadresse = connectionSocket.getInetAddress().toString();
			String localAdress = connectionSocket.getLocalAddress().toString();

			outToClient.writeBytes(capitalizedSentence + " - IPadresse = " + IPadresse + " - localAdress = " + localAdress + '\n');
		}

	}

}
