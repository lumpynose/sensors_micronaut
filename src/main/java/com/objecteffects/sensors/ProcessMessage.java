package com.objecteffects.sensors;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.jackson.databind.JacksonDatabindMapper;
import io.micronaut.context.annotation.Prototype;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ProcessMessage {
    private final static Logger log = LoggerFactory
        .getLogger(ProcessMessage.class);

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    private static TUnit tunit = TUnit.Fahrenheit; // MainPaho.getTunit();

    @Inject
    JacksonDatabindMapper mapper;

    // private static Map<String, String> propSensors =
    // MainPaho.getPropSensors();

    @PostConstruct
    @SuppressWarnings("static-method")
    public void initialize() {
        log.info("initialize");
    }

    public SensorData processData(final String topic, final String data)
        throws IOException {
        final String topic_trimmed = StringUtils.substringAfterLast(topic, "/");

        log.info("topic: {}", topic_trimmed);

        final SensorData target =
            this.mapper.readValue(data.getBytes(), SensorData.class);

//        if (!propSensors.containsKey(topic_trimmed)) {
//            return null;
//        }

        // target.setSensorName(propSensors.get(topic_trimmed));
        target.setSensorName(topic_trimmed);
        target.setTemperatureShow(tunit.convert(target));
        target.setTemperatureLetter(tunit.toString());

        final LocalDateTime dateTime = LocalDateTime.now();

        target.setTimestamp(this.dtf.format(dateTime));

        log.info("decoded data: {}", target.toString());

        return target;
    }
}
