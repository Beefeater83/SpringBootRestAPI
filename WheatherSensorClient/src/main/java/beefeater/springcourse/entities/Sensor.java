package beefeater.springcourse.entities;

import beefeater.springcourse.entities.Measurement;

import java.util.List;

public class Sensor {

   private String name;

    public Sensor( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
    public Sensor() {
    }
}
