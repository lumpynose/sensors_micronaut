package com.objecteffects.mqtt;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

/*
 * This is all wrong.  Needs to inject.
 */
public class MqttMain {
    public static void main(String[] args) throws MqttException {
        String serverIp = "192.168.50.5";
        String port = "1883";
        String[] topics = { "rtl_433/temperature/+", "zigbee/temperature/+" };
        int qos = 1;

        String broker = "tcp://" + serverIp + ":" + port;

        MqttClient client = new MqttConnect().connect(broker);
        
        try {
            new MqttListen().listen(client, topics, qos, new MqttListener());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
