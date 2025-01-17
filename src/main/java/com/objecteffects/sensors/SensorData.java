package com.objecteffects.sensors;

import java.io.Serializable;
import java.util.Objects;

import io.micronaut.core.annotation.Introspected;

@Introspected 
public class SensorData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String sensorName = "";
    private Float temperature_F; // = Float.NaN;
    private Float temperature; // = Float.NaN;
    private Float temperatureShow;
    private String temperatureLetter;
    private Float  humidity; // = Float.NaN;
    private int battery_ok = 0;
    private String timestamp;
    private int illuminance_lux = Integer.MIN_VALUE;
    private Float pressure; // = Float.NaN;
    private int voc = Integer.MIN_VALUE;

    public int getVoc() {
        return this.voc;
    }

    public void setVoc(final int _voc) {
        this.voc = _voc;
    }

    public Float getPressure() {
        return this.pressure;
    }

    public void setPressure(final Float _pressure1) {
        this.pressure = _pressure1;
    }

    public int getLuminance() {
        return this.illuminance_lux;
    }

    public void setLuminance(final int _luminance) {
        this.illuminance_lux = _luminance;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final String _timestamp) {
        this.timestamp = _timestamp;
    }

    public String getSensorName() {
        return this.sensorName;
    }

    public void setSensorName(final String _sensorName) {
        this.sensorName = _sensorName;
    }

    public Float getTemperature_F() {
        return this.temperature_F;
    }

    public void setTemperature_F(final Float _temperature_F) {
        this.temperature_F = _temperature_F;
    }

    public Float getTemperature() {
        return this.temperature;
    }

    public void setTemperature(final Float _temperature) {
        this.temperature = _temperature;
    }

    public Float getTemperatureShow() {
        return this.temperatureShow;
    }

    public void setTemperatureShow(final Float _temperatureShow) {
        this.temperatureShow = _temperatureShow;
    }

    public String getTemperatureLetter() {
        return this.temperatureLetter;
    }

    public void setTemperatureLetter(final String _temperatureLetter) {
        this.temperatureLetter = _temperatureLetter;
    }

    public Float getHumidity() {
        return this.humidity;
    }

    public void setHumidity(final Float _humidity) {
        this.humidity = _humidity;
    }

    public int getBattery_ok() {
        return this.battery_ok;
    }

    public void setBattery_ok(final int _battery_ok) {
        this.battery_ok = _battery_ok;
    }

    @Override
    public String toString() {
        return "SensorData [sensorName=" + this.sensorName + ", temperature_F="
                + this.temperature_F + ", temperature=" + this.temperature
                + ", temperatureShow=" + this.temperatureShow
                + ", temperatureLetter=" + this.temperatureLetter
                + ", humidity=" + this.humidity + ", battery_ok="
                + this.battery_ok + ", timestamp=" + this.timestamp
                + ", illuminance_lux=" + this.illuminance_lux + ", pressure="
                + this.pressure + ", voc=" + this.voc + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.sensorName);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final SensorData other = (SensorData) obj;

        return other.sensorName.compareToIgnoreCase(this.sensorName) == 0;
    }
}
