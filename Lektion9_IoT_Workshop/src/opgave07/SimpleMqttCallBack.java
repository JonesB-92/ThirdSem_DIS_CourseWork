package opgave07;
//opg7pgm

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class SimpleMqttCallBack implements MqttCallback {
    int status = 0;

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String res = new String(mqttMessage.getPayload());
        System.out.println(res);
        // res indeholder en m ling som et JSON-object
        // put real stuff here     < --------    !!!!!!!!!!
        JSONObject jo  = new JSONObject(res);
        JSONObject jo2 = jo.getJSONObject(("AM2301"));

        double tempD = jo2.getDouble("Temperature");
        double humD = jo2.getDouble("Humidity");

        System.out.println("Temperature: " + tempD + "\n" + "Humidity: " + humD);
        if (humD >= 50.0 && status <= 0) {
            status = 1;
            MainMQTTCallBack.publishMessage(MainMQTTCallBack.sampleClient, "cmnd/grp5227/Power1", String.valueOf(status));
        } else if (humD < 50.0 && status > 0){
            status = 0;
            MainMQTTCallBack.publishMessage(MainMQTTCallBack.sampleClient, "cmnd/grp5227/Power1", String.valueOf(status));
        }
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
}
