import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Opgave7_2_Valuta {
    //Prøv web-siden
    //https://valutakurser.dk
    //både fra en browser og efterfølgende fra ovenstående programstump.
    //Lav nu programmet om, så det udelukkende udskriver valuta-kursen mellem US-dollars og den
    //danske krone.
    public static void main(String[] args) throws IOException {

        URL url = new URL("https://www.valutakurser.dk/");
        InputStreamReader r = new InputStreamReader(url.openStream());
        BufferedReader in = new BufferedReader(r);
        String str;
//            if (str.contains("")) {
        while ((str = in.readLine()) != null) {
            if (str.contains("<div class=\"currencyItem_actualValueContainer__V41t2\"></div>")) {

                String søg = "\"currencyItem_actualValueContainer__V41t2\">";
//                int stringStart = str.indexOf(søg);
//                int valueStart = søg.indexOf(">");
//                int valueEnd = søg.indexOf("<");
//                søg.substring()
                System.out.println(str);
            }
        }
        in.close();
    }

    //{"currencyName":"Amerikanske dollar","symbol":"USD",
    // "flagName":"USD","currencyChange":0.3239109481900426,"actualValue":632.89,"date":"03-02-2026"}

}
