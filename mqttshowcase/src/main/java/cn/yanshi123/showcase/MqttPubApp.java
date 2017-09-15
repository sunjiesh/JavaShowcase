package cn.yanshi123.showcase;

import java.util.Date;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPubApp {

	public static void main(String[] args) {
		int messageSize = 10000;
		Date startTime = new Date();
		pubMessageWithOneClient(messageSize);
		Date endTime = new Date();
		System.out.println("开始时间=" + startTime);
		System.out.println("结束时间=" + endTime);


	}

	public static void pubMessage(int messageSize) {
		for(int index=0;index<messageSize;index++){
			
			try {
				String broker = "tcp://localhost:8883";
				MqttClient mqttClient = new MqttClient(broker, MqttClient.generateClientId());
				mqttClient.connect();
				String mqttPayloadStr = "hello world!"+UUID.randomUUID().toString();
				MqttMessage message = new MqttMessage();
				message.setPayload(mqttPayloadStr.getBytes());
				mqttClient.publish("test", message);
				mqttClient.disconnect();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void pubMessageWithOneClient(int messageSize) {

		try {
			String broker = "tcp://localhost:8883";
			MqttClient mqttClient = new MqttClient(broker, MqttClient.generateClientId());
			mqttClient.connect();
			for (int index = 0; index < messageSize; index++) {
				String mqttPayloadStr = "hello world!" + UUID.randomUUID().toString();
				MqttMessage message = new MqttMessage();
				message.setPayload(mqttPayloadStr.getBytes());
				mqttClient.publish("test", message);
			}

			mqttClient.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

}
