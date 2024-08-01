package com.objecteffects.mqtt;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import com.objecteffects.sensors.Sensors;

import org.awaitility.Awaitility;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class MqttTest {
    final static Logger log = LoggerFactory.getLogger(MqttTest.class);

    @Inject
    EmbeddedServer server;

    @Inject
    ApplicationContext context;

    @Inject
    MqttConnect connect;

    @Inject
    MqttListen listen;

    @Inject
    MqttListener listener;

    @Inject
    Sensors sensors;

    @Test
    void testItWorks() throws MqttException {
        final String broker = "tcp://192.168.50.3:1883";
        final String[] topics =
            { "rtl_433/temperature/+", "zigbee/temperature/+" };
        final int qos = 1;

        MqttClient client = this.connect.connect(broker);
        this.listener.setClient(client);
        this.listen.listen(client, topics, qos, new MqttListener());

        Awaitility.setDefaultPollInterval(1000, MILLISECONDS);

        Awaitility.await().until(() -> {
            log.info("trying ...");

            return !this.sensors.getSensors().isEmpty();
        });
    }
}
