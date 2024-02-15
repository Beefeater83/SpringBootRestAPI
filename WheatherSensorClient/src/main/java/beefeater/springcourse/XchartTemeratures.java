package beefeater.springcourse;

import beefeater.springcourse.entities.Measurement;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class XchartTemeratures {
    public static void main(String[] args) {
        RestTemplate template =new RestTemplate();
        List<Measurement> measurementsList;
        double[]temperatures;
        double[]days;
         //get data
        String url = "http://localhost:8080/measurements";
        ResponseEntity<Measurement[]> response = template.getForEntity(url, Measurement[].class);
        Measurement[] measurements = response.getBody();
        if(measurements !=null){
            measurementsList= Arrays.asList(measurements);
            temperatures=new double[measurementsList.size()];
            days= new double[measurementsList.size()];
            for (int i = 0; i <measurementsList.size(); i++) {
                temperatures[i]=measurementsList.get(i).getValue();
                days[i] = i + 1;
            }
            XYChart chart = new XYChartBuilder()
                    .width(800)
                    .height(600)
                    .title("График температур по дням")
                    .xAxisTitle("Дни")
                    .yAxisTitle("Температура").build();

            chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
            chart.getStyler().setXAxisLabelRotation(45);

            chart.addSeries("Температуры", days, temperatures);
            new SwingWrapper<>(chart).displayChart();

        }else {
            System.out.println("Не удалось получить данные из сервера.");
        }


    }
}
