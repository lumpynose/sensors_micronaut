package com.objecteffects.sensors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * the field temperature_F is set for rs433 devices while the field
 * temperature (Celsius) is set for zigbee devices. I'm assuming that a
 * sensor has one of the two temperature values.
 */
public enum TUnit {
    Celsius("C") {
        @Override
        public float convert(final SensorData sensor) {
            if (Float.isFinite(sensor.getTemperature())) {
                return sensor.getTemperature();
            }

            if (Float.isFinite(sensor.getTemperature_F())) {
                final float celsius = (float) ((sensor.getTemperature_F() - 32.0)
                        * (5.0 / 9.0));

                return celsius;
            }

            return Float.NaN;
        }
    },
    Fahrenheit("F") {
        @Override
        public float convert(final SensorData sensor) {
            if (Float.isFinite(sensor.getTemperature_F())) {
                return sensor.getTemperature_F();
            }

            if (Float.isFinite(sensor.getTemperature())) {
                final float fahr = (float) (sensor.getTemperature() * (9.0 / 5.0)
                        + 32.0);

                return fahr;
            }

            return Float.NaN;
        }
    },
    Kelvin("K") {
        @Override
        public float convert(final SensorData sensor) {
            if (Float.isFinite(sensor.getTemperature_F())) {
                final float kelvin = (float) ((sensor.getTemperature_F() - 32)
                        * (5.0 / 9.0) + 273.15);

                return kelvin;
            }

            if (Float.isFinite(sensor.getTemperature())) {
                final float kelvin = (float) (sensor.getTemperature() + 273.15);

                return kelvin;
            }

            return Float.NaN;
        }
    };

    private final String letter;

    private static final Map<String, TUnit> ENUM_MAP;

    TUnit(final String _letter) {
        this.letter = _letter;
    }

    @Override
    public String toString() {
        return this.letter;
    }

    static {
        final Map<String, TUnit> map = new HashMap<>();

        for (final TUnit tunit : TUnit.values()) {
            map.put(tunit.toString().toLowerCase(), tunit);
        }

        ENUM_MAP = Collections.unmodifiableMap(map);

        // Stream.of(TUnit.values()).collect(toMap(Enum::name, identity()));
    }

    public static TUnit get(final String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }

    public abstract float convert(final SensorData sensor);
}
