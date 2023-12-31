package telran.microservices.order.request;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.microservices.order.request.service.CreateOrderRequestNew;


@SpringBootApplication
@Slf4j
public class CreateOrderRequestAppl {


	@Autowired
	StreamBridge streamBridge;
	@Value("${app.analyze.close.producer.binding.name:createOrderRequestNewProducer-out-0}")
	String bindingName;
	
	@Autowired
	CreateOrderRequestNew createRequest;
	
	public static void main(String[] args) {
		SpringApplication.run(CreateOrderRequestAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerDemand> createOrderRequestNewConsumer(){
		return this::createRequest;
		
	}
	
	void createRequest(ContainerDemand demand) {
		OrderRequestNew orderRequest = createRequest.createRequestByDemand(demand);
		if(orderRequest!=null) {
			streamBridge.send(bindingName, orderRequest);
			log.debug("Request for new order for container {} sent", demand.containerId());
		} else {
			log.trace("No request for container {} sent", demand.containerId());
		}
	}

}
