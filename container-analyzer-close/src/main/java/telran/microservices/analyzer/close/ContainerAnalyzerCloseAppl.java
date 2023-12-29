package telran.microservices.analyzer.close;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.analyzer.close.service.ContainerAnalyzeClose;

@SpringBootApplication
@Slf4j
public class ContainerAnalyzerCloseAppl {

	@Autowired
	StreamBridge streamBridge;
	@Value("${app.analyze.close.producer.binding.name:orderAnalyzeCloseProducer-out-0}")
	String bindingName;
	
	@Autowired
	ContainerAnalyzeClose orderAnalyzeClose;

	public static void main(String[] args) {
		SpringApplication.run(ContainerAnalyzerCloseAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerSensorChanged> orderAnalyzeCloseConsumer(){
		return this::analyze;
		
	}
	void analyze(ContainerSensorChanged sensorData) {
		List<OrderRequestClose> orderList = orderAnalyzeClose.sensorDataAnalyzeClose(sensorData);
		if(orderList!=null) {
			orderList.forEach(order -> {
				streamBridge.send(bindingName,order);
				log.debug("Request to close order {} for container {} sent", order.order_id(), sensorData.containerId());				
				});			
		} else {
			log.trace("No request for container {} sent", sensorData.containerId());
		}
			
	}

}
