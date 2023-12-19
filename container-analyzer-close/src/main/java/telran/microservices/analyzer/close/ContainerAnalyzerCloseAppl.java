package telran.microservices.analyzer.close;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.analyzer.close.service.ContainerAnalyzeClose;

@SpringBootApplication
public class ContainerAnalyzerCloseAppl {

	@Autowired
	StreamBridge streamBridge;
	@Value("${app.sensor.producer.binding.name:orderCloseRequest-out-0}")
	String bindingName;
	
	@Autowired
	ContainerAnalyzeClose orderAnalyzeClose;

	public static void main(String[] args) {
		SpringApplication.run(ContainerAnalyzerCloseAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerSensor> containerInfoConsumer(){
		return this::analyze;
		
	}
	void analyze(ContainerSensor sensorData) {
		OrderRequestClose res=orderAnalyzeClose.SensorDataAnalyzeClose(sensorData);
		if(res!=null) {
			streamBridge.send(bindingName,res);
		}
	}

}
