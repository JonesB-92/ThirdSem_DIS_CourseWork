import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Opgave7_1 {
    //Opgave 7.1
    //Skriv et Java-program, der henter en Web-side fra en bestemt web-server og udskriver den på
    //skærmen. Du skal gøre det i et simpelt consol-program.

    /**
     * screen-scraping
     */
    //Denne webside viser hvor mange gange den pågældende side har været vist.
    //Lav programmet om, så du kun udskriver selve tallet
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://dis.students.dk/example1.php");
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader in = new BufferedReader(r);
        String str;

        while ((str = in.readLine()) != null) {
            if (str.toLowerCase().contains("everyone")) {
                String[] strings = str.split(" ");
                System.out.println(strings[5]);
            }
        }
        in.close();
    }
}


