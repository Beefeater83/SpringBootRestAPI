package beefeater.springcourse;
import beefeater.springcourse.entities.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

public class PostData {
    public static void main(String[] args) {
        //post data
        RestTemplate template =new RestTemplate();
        String url = "http://localhost:8080/measurements/add";


        Random random = new Random();

        for (int i = 1; i<51; i++) {
            boolean raining = random.nextBoolean();
            double temperature = random.nextDouble()*98+1.0;
            Sensor sensor = new Sensor("sensor2");
            Measurement measurement = new Measurement(temperature, raining,sensor);
            HttpEntity<Measurement>requestToSend = new HttpEntity<>(measurement);
            System.out.println("Отправлено: "+ requestToSend.toString());
            ResponseEntity<String> response = template.postForEntity(url, requestToSend, String.class);
            System.out.println(response);
        }







    }
}
