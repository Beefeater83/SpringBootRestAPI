package ua.diakonov.SpringBootRESTWeatherSensor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.diakonov.SpringBootRESTWeatherSensor.models.Measurement;
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
