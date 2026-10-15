package MQTT_Opgave14;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Publisher {
    static MqttClient sampleClient;

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String broker = "tcp://192.168.0.116:1883";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            //her skal indsættes noget
            /**
             * • Brugeren af publisher-programmet skal i en løkke kunne indtaste topic og en besked og den
             * pågældende besked skal så komme ud til alle de subscribere, der lytter på den pågældende
             * topic.
             */
            // How to send content c m on topic t
            while (sampleClient.isConnected()) {
                String t = "Ionas";
                String c = inFromUser.readLine();

                MqttMessage message = new MqttMessage();
                message.setPayload(c.getBytes());
                sampleClient.publish(t, message);
                System.out.println("Message published");

                if (c.equalsIgnoreCase("STOP")) {
                    sampleClient.disconnect();
                }

            }
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch (MqttPersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
