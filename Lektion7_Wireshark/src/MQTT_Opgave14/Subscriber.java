package MQTT_Opgave14;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Subscriber {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String broker = "tcp://192.168.0.116:1883";

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.setCallback(new SimpleMqttCallBack());
            sampleClient.connect(connOpts);
            System.out.println("Connected");


            /**
             *  Subscriberne skal kunne indtaste et topic og hvor mange sekunder man ønsker at lytte på det
             * pågældende topic. Når tiden er udløbet, skal programmet slutte
             */
            // Her indsætter du din subscribe kode
            String topic = "";
            while (!topic.equalsIgnoreCase("stop")) {
                System.out.println("Indtast topic");
                topic = inFromUser.readLine();
                sampleClient.subscribe(topic);

                boolean antalSekFuncs = false;
                while (!antalSekFuncs) {
                    try {
                        System.out.println("Indtast sub-time");
                        int antalSekSubbed = Integer.parseInt(inFromUser.readLine());
                        Thread.sleep(antalSekSubbed);
                        antalSekFuncs = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Sub-time skal være et tal. Prøv igen");
                    }
                }
            }
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
