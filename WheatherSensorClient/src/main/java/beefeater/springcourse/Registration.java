package beefeater.springcourse;

import beefeater.springcourse.entities.Sensor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Registration {
    public static void main(String[] args) throws IOException {
        RestTemplate template =new RestTemplate();
        Map<String, String> jsonToSend = new HashMap<>();

        //post registration
        String url = "http://localhost:8080/sensors/registration";
        String sensorName;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите имя нового сенсора:");
        sensorName= reader.readLine();
        if (sensorName != null && !sensorName.isEmpty()){
            jsonToSend.put("name", sensorName);
        }else System.out.println("вы не ввели имя сенсора");
        reader.close();

        HttpEntity<Map<String,String>> request = new HttpEntity<>(jsonToSend);
        String response = template.postForObject(url,request, String.class);
        System.out.println(response);
    }
}