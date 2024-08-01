package com.objecteffects.web;

import com.objecteffects.mqtt.MqttConnect;
import com.objecteffects.mqtt.MqttListen;
import com.objecteffects.mqtt.MqttListener;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/*
 * Use this when using tomcat.
 */

@Singleton
public class MqttStartupContainer
    implements ApplicationEventListener<StartupEvent> {
    final static Logger log =
        LoggerFactory.getLogger(MqttStartupContainer.class);

    final String broker = "tcp://192.168.50.3:1883";
    final String[] topics = { "rtl_433/temperature/+", "zigbee/temperature/+" };
    final int qos = 1;

    @Inject
    MqttConnect connect;

    @Inject
    MqttListen listen;

    @Inject
    MqttListener listener;

    @PostConstruct
    @SuppressWarnings("static-method")
    public void initialize() {
        log.info("initialize");
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        log.info("MqttStartup StartupEvent: {}", event);

        try {
            MqttClient client = this.connect.connect(this.broker);

            this.listener.setClient(client);

            this.listen.listen(client, this.topics, this.qos, this.listener);
        }
        catch (MqttException e) {
            log.error("exception: {}", e);
        }
    }
}
