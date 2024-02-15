package beefeater.springcourse.entities;

import java.text.DecimalFormat;

public class Measurement {
    private double value;

    private boolean raining;

    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(double value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.0");
        return "{" +
                "value=" + df.format(value) +
                ", raining=" + raining +
                ", sensor=" + sensor.getName() +
                '}' + '\n';
    }
}
