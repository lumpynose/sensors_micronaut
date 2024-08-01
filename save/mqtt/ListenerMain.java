package com.objecteffects.mqtt;


import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class ListenerMain {
    final static Logger log = LoggerFactory.getLogger(ListenerMain.class);

    public static void main(String[] args) throws MqttException {
        String server = "192.168.50.5";
        String port = "1883";
        String[] topics = { "rtl_433/temperature/+", "zigbee/temperature/+" };
        int qos = 1;
        String broker;

        broker = "tcp://" + server + ":" + port;

        ConnectAndListen listener = new ConnectAndListen();
        MqttClient client = new ConnectAndListen().connect("192.168.50.5");
        
        try {
            listener.connectAndListen(broker, topics, qos);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
