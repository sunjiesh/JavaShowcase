package cn.yanshi123.showcase;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Hello world!
 *
 */
public class MqttSubApp {
	public static void main(String[] args) {
		String broker = "tcp://localhost:8883";
		MqttClient mqttClient = null;
		try {
			mqttClient = new MqttClient(broker, MqttClient.generateClientId());
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(true);
			options.setConnectionTimeout(30);
			options.setKeepAliveInterval(60);
			mqttClient.connect(options);
			mqttClient.setCallback(new MqttCallback(){

				public void connectionLost(Throwable cause) {
					System.err.println("mqtt connectionLost,reason is " + cause.getMessage());
				}

				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("receive a mqtt message,message is " + message.toString());
				}

				public void deliveryComplete(IMqttDeliveryToken token) {

				}});
			mqttClient.subscribe("test");
			System.out.println("mqtt sub success");
		} catch (MqttException e) {
			e.printStackTrace();
		} finally {
			if (mqttClient != null && mqttClient.isConnected()) {
				try {
					mqttClient.disconnect();
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
