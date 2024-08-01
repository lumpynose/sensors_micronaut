package com.objecteffects.sensors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.core.annotation.Introspected;
import jakarta.inject.Singleton;

@Singleton
@Introspected
public class Sensors {
    final static Logger log = LoggerFactory.getLogger(Sensors.class);

    private final static Map<String, SensorData> sensorsMap =
        new ConcurrentHashMap<>();

    @SuppressWarnings("static-method")
    public void addSensor(final SensorData data) {
        sensorsMap.put(data.getSensorName(), data);
    }

    public Map<String, SensorData> getSensors() {
        log.info("sensorsMap values: {}", sensorsMap.values());

        return sensorsMap;
    }
}
