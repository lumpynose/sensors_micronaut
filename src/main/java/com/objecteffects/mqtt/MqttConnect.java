package com.objecteffects.mqtt;

import java.util.UUID;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class MqttConnect {
    final static Logger log = LoggerFactory.getLogger(MqttConnect.class);

    private static MemoryPersistence persistence = new MemoryPersistence();

    @SuppressWarnings("static-method")
    public MqttClient connect(String broker) throws MqttException {
        MqttClient client;
        String clientId = UUID.randomUUID().toString();

        try {
            log.info("Connecting to MQTT broker: {}", broker);

            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(false);

            client = new MqttClient(broker, clientId, persistence);
            client.connect(connOpts);

            log.info("Connected");
        }
        catch (MqttException ex) {
            log.error("exception: {}", ex);

            throw ex;
        }

        return client;
    }

    @SuppressWarnings("static-method")
    public void disconnect(MqttClient client) {
        try {
            client.disconnect();
            log.info("Disconnected");
        }
        catch (Exception ex) {
            log.error("exception: {}", ex);
        }
    }
}
