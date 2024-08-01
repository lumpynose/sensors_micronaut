package com.objecteffects.mqtt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;

@Singleton
public class MqttListen {
    final static Logger log = LoggerFactory.getLogger(MqttListen.class);

    @SuppressWarnings("static-method")
    public void listen(final MqttClient client, final String[] topics,
        final int qos,
        final IMqttMessageListener listener) {

        final List<MqttSubscription> subs = new ArrayList<>();

        for (String topic : topics) {
            log.info("Subscribing to topic {}", topic);

            subs.add(new MqttSubscription(topic, qos));
        }

        final IMqttMessageListener[] listeners = new MqttListener[subs.size()];
        Arrays.fill(listeners, listener);

        try {
            client.subscribe(subs.toArray(new MqttSubscription[0]), listeners);
        }
        catch (Exception ex) {
            log.error("exception: {}", ex);
        }
    }
}
