package ua.diakonov.SpringBootRESTWeatherSensor.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.diakonov.SpringBootRESTWeatherSensor.dto.MeasurementDTO;
import ua.diakonov.SpringBootRESTWeatherSensor.dto.SensorDTO;
import ua.diakonov.SpringBootRESTWeatherSensor.models.Measurement;
import ua.diakonov.SpringBootRESTWeatherSensor.models.Sensor;
import ua.diakonov.SpringBootRESTWeatherSensor.services.MeasurementService;
import ua.diakonov.SpringBootRESTWeatherSensor.services.SensorService;
import ua.diakonov.SpringBootRESTWeatherSensor.util.MeasurementNotCreatedException;
import ua.diakonov.SpringBootRESTWeatherSensor.util.SensorErrorResponse;
import ua.diakonov.SpringBootRESTWeatherSensor.util.SensorNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
    return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(measurement.getValue());
        measurementDTO.setRaining(measurement.isRaining());
        Sensor sensor=new Sensor();
        sensor.setName(measurement.getSensor().getName());
        measurementDTO.setSensor(sensor);
        //return modelMapper.map(measurement, MeasurementDTO.class); вместо этого наколхозил сеттерами чтобы избежать EAGER загрузки
        return measurementDTO;
    }
    @GetMapping("/rainydays")
    public Map<String, Integer> rainyDaysCount(){
        Map<String,Integer>rainyDays=new HashMap<>();
        rainyDays.put("Дождливых дней было", measurementService.rainyDaysCheck());
        return rainyDays;
    }


    // шлю {"value": 29.0, "raining": true, "sensor":{"name":"sensor1"}}
    // http://localhost:8080/measurements/add
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError>errors = bindingResult.getFieldErrors();
            for(FieldError error :errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotCreatedException(errorMsg.toString());
        } else if (sensorService.findByName(measurementDTO.getSensor().getName())==null) {
            throw new SensorNotFoundException();
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        Sensor sensor = sensorService.findByName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);
        if(sensor !=null){
            measurement.getSensor().setId(sensor.getId());
        }else throw new SensorNotFoundException();
        return measurement;
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse>handleException(MeasurementNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse>handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse("Сенсор с таким названием не зарегестрирован", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
