package MQTT_Opgave14;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

public class SimpleMqttCallBack implements MqttCallback{
	int status = 1;

	 public void connectionLost(Throwable throwable) {
		    System.out.println("Connection to MQTT broker lost!");
		  }

		  public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
			String res= new String(mqttMessage.getPayload());
		    //Her skal også indsættes noget
			System.out.println(res);

		  }

		  public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		    // not used in this example
		  }
}
