package beefeater.springcourse;

import beefeater.springcourse.entities.Measurement;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetData {
    public static void main(String[] args) {
        RestTemplate template =new RestTemplate();
        List<Measurement> measurementsList;

        //get data
        String url = "http://localhost:8080/measurements";
        ResponseEntity<Measurement[]> response = template.getForEntity(url, Measurement[].class);
        Measurement[] measurements = response.getBody();
        if(measurements !=null){
            measurementsList=Arrays.asList(measurements);
            System.out.println(measurementsList.toString());
        }else {
            System.out.println("Не удалось получить данные из сервера.");
        }

        //get rainy days
        String url_rain = "http://localhost:8080/measurements/rainydays";
        String response_rain = template.getForObject(url_rain, String.class);
        System.out.println(response_rain);
    }
}
