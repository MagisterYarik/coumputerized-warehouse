package telran.microservices.analyzer.close;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
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
	Consumer<ContainerSensorChanged> containerInfoConsumer(){
		return this::analyze;
		
	}
	void analyze(ContainerSensorChanged sensorData) {
		List<OrderRequestClose> res=orderAnalyzeClose.sensorDataAnalyzeClose(sensorData);
		if(res!=null) {
			streamBridge.send(bindingName,res);
		}
	}

}
