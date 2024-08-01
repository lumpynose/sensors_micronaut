package com.objecteffects.mqttebruno;

import com.objecteffects.web.MqttStartupCli;

import org.eclipse.paho.mqttv5.client.IMqttMessageListener;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ebruno
 */
public class ListenerEBruno implements IMqttMessageListener {
    final static Logger log = LoggerFactory.getLogger(MqttStartupCli.class);

    public static int qos = 1;
    public static String server = "192.168.50.5";
    public static String port = "1883";
    public static String broker = "tcp://192.168.50.5:1883";
    public static String clientId = "mqtt_listener";
    public static String topic = "temperature";

    public static MemoryPersistence persistence = new MemoryPersistence();
    public static MqttClient client;

    public static void main(String[] args) {
        // See if a topic names was provided on the command line
        if (args.length == 1) {
            topic = args[0];
        }
        else {

            for (int i = 0; i < args.length; i++) {

                switch (args[i]) {
                case "server":
                    server = args[++i];
                    break;

                case "port":
                    port = args[++i];
                    break;

                case "topic":
                    topic = args[++i];
                    break;
                }

            }
        }

        broker = "tcp://" + server + ":" + port;

        ListenerEBruno listener = new ListenerEBruno();

        try {
            listener.connect();
            listener.listen(topic);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListenerEBruno() {
    }

    public void connect() throws MqttException {
        try {
            log.info("Connecting to MQTT broker: " + broker);

            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setCleanStart(false);

            client = new MqttClient(broker, clientId, persistence);
            client.connect(connOpts);

            log.info("Connected");
        }
        catch (MqttException me) {
            log.info("reason " + me.getReasonCode());
            log.info("msg " + me.getMessage());
            log.info("loc " + me.getLocalizedMessage());
            log.info("cause " + me.getCause());
            log.info("excep " + me);
            me.printStackTrace();
            throw me;
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            log.info("Disconnected");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen(String topic) throws Exception {
        try {
            log.info("Subscribing to topic " + topic);

            MqttSubscription sub =
                new MqttSubscription(topic, qos);

            IMqttToken token = client.subscribe(
                new MqttSubscription[] { sub },
                new IMqttMessageListener[] { this });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage)
        throws Exception {
        String messageTxt = new String(mqttMessage.getPayload());
        log.info("Message on " + topic + ": '" + messageTxt + "'");

        MqttProperties props = mqttMessage.getProperties();
        String responseTopic = props.getResponseTopic();

        if (responseTopic != null) {
            log.info("--Response topic: " + responseTopic);
            String corrData = new String(props.getCorrelationData());

            MqttMessage response = new MqttMessage();
            props = new MqttProperties();
            props.setCorrelationData(corrData.getBytes());
            String content = "Got message with correlation data " + corrData;
            response.setPayload(content.getBytes());
            response.setProperties(props);
            client.publish(responseTopic, response);
        }
    }
}
