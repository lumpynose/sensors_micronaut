package com.objecteffects.mqtt;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.objecteffects.sensors.ProcessMessage;
import com.objecteffects.sensors.SensorData;
import com.objecteffects.sensors.Sensors;

import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttPersistenceException;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class MqttListener implements IMqttMessageListener {
    final static Logger log = LoggerFactory.getLogger(MqttListener.class);

    private MqttClient client;

    @Inject
    ProcessMessage processMessage;

    @Inject
    Sensors sensors;

    private final Map<String, SensorData> sensorsMap =
        new ConcurrentHashMap<>();

    @PostConstruct
    @SuppressWarnings("static-method")
    public void initialize() {
        log.info("initialize");
    }

    @Override
    public void messageArrived(final String topic,
        final MqttMessage mqttMessage)
        throws MqttPersistenceException, MqttException, IOException {
        final String messageTxt = new String(mqttMessage.getPayload());
        log.info("Message on {}: '{}'", topic, messageTxt);

        final SensorData target =
            this.processMessage.processData(topic, messageTxt);
        log.info("target: {}", target);

        this.sensors.addSensor(target);

        this.sensorsMap.put(target.getSensorName(), target);

        final MqttProperties props = mqttMessage.getProperties();
        final String responseTopic = props.getResponseTopic();

        if (responseTopic != null) {
            log.info("--Response topic: {}", responseTopic);
            final String corrData = new String(props.getCorrelationData());

            final MqttMessage response = new MqttMessage();
            final MqttProperties responseProps = new MqttProperties();
            responseProps.setCorrelationData(corrData.getBytes());
            final String content =
                "Got message with correlation data " + corrData;
            response.setPayload(content.getBytes());
            response.setProperties(props);

            this.client.publish(responseTopic, response);
        }
    }

    public Sensors getSensors() {
        return this.sensors;
    }

    public Map<String, SensorData> getSensorsMap() {
        return this.sensorsMap;
    }

    public void setClient(final MqttClient _client) {
        this.client = _client;
    }
}
