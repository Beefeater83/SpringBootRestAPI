package ua.diakonov.SpringBootRESTWeatherSensor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.diakonov.SpringBootRESTWeatherSensor.models.Measurement;
import ua.diakonov.SpringBootRESTWeatherSensor.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAll(){
    return measurementRepository.findAll();
    }
    @Transactional
    public void save(Measurement measurement) {
        addMissigData(measurement);
        measurementRepository.save(measurement);
    }

    private void addMissigData(Measurement measurement) {
    measurement.setCreatedAt(LocalDateTime.now());
    }
    public int rainyDaysCheck(){
        int days=0;
        List<Measurement>measurements=findAll();
        for (Measurement day:measurements){
            if (day.isRaining()){
                days++;
            }
        }
        return days;
    }
}
