package telran.microservices.database.create.order;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.microservices.database.create.order.service.CreateOrder;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
@EntityScan(basePackages = {"telran"})
public class CreateOrderAppl {
	
	@Autowired
	CreateOrder createOrder;
	
	public static void main(String[] args) {
		SpringApplication.run(CreateOrderAppl.class, args);

	}
	
	@Bean
	Consumer<OrderRequestNew> createOrderConsumer(){
		return request -> createOrder.createNewOrder(request);
		
	}


}
