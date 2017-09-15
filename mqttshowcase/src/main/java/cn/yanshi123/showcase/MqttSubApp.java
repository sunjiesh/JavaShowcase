package cn.yanshi123.showcase;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Hello world!
 *
 */
public class MqttSubApp {
	public static void main(String[] args) {
		String broker = "tcp://localhost:8883";
		try {
			MqttClient mqttClient = new MqttClient(broker, MqttClient.generateClientId());
			mqttClient.connect();
			mqttClient.setCallback(new MqttCallback(){

				public void connectionLost(Throwable cause) {
					System.err.print("mqtt connectionLost,reason is " + cause.getMessage());
				}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("receive a mqtt message,message is " + message.toString());
				}

				public void deliveryComplete(IMqttDeliveryToken token) {
					

				}});
			mqttClient.subscribe("test");
			System.out.println("mqtt sub successs");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
