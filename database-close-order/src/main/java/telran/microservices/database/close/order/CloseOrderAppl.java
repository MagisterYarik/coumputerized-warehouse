package telran.microservices.database.close.order;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.database.close.order.service.CloseOrder;

@SpringBootApplication
public class CloseOrderAppl {
	
	@Autowired
	CloseOrder closeOrder;
	
	public static void main(String[] args) {
		SpringApplication.run(CloseOrderAppl.class, args);

	}
	
	@Bean
	Consumer<OrderRequestClose> closeOrderConsumer(){
		return request -> closeOrder.closeOnRequest(request);
		
	}

}
