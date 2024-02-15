package ua.diakonov.SpringBootRESTWeatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.diakonov.SpringBootRESTWeatherSensor.dto.SensorDTO;
import ua.diakonov.SpringBootRESTWeatherSensor.models.Sensor;
import ua.diakonov.SpringBootRESTWeatherSensor.repositories.SensorRepository;
import ua.diakonov.SpringBootRESTWeatherSensor.util.SensorExistException;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void save(Sensor sensor) {
        Sensor existingSensor = findByName(sensor.getName());
        if (existingSensor == null) {
            sensorRepository.save(sensor);
        } else throw new SensorExistException();
    }

}
