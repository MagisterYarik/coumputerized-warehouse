package telran.microservices.database.close.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.jdbc.Sql;

import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.database.close.order.repo.OrderHeaderRepo;
import telran.microservices.database.close.order.service.CloseOrder;

@SpringBootTest
@Sql(scripts = "db-test-script.sql")
public class CloseOrderTest {
	@Autowired
	InputDestination producer;
	private String consumerBindingName = "closeOrderConsumer-in-0";
	
	@Autowired
	private OrderHeaderRepo orderRepo;
	@Value("${database.order.status.closed:23}")
	private short statusClosed;

	private static final long ORDER_ID_EXISTS = 80085;
	private static final long ORDER_ID_CANT_BE_CLOSED = 575;
	private static final long ORDER_ID_NONE = 2200;
	private static final OrderRequestClose ORDER_REQUEST_EXISTS = new OrderRequestClose(ORDER_ID_EXISTS, (short)30);
	private static final OrderRequestClose ORDER_REQUEST_CANT_BE_CLOSED = new OrderRequestClose(ORDER_ID_CANT_BE_CLOSED , (short)30);
	private static final OrderRequestClose ORDER_REQUEST_NONE = new OrderRequestClose(ORDER_ID_NONE, (short)30);
	private static final OrderDataHeader EXISTING_ORDER_EXISTS = new OrderDataHeader(ORDER_ID_EXISTS, (short)1, 1000, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_CANT_BE_CLOSED = new OrderDataHeader(ORDER_ID_EXISTS, (short)21, 1000, 1518, 35.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	
	@Test
	void orderExistsTest() {
		producer.send(new GenericMessage<OrderRequestClose>(ORDER_REQUEST_EXISTS), consumerBindingName );
		assertEquals(statusClosed, orderRepo.findById(ORDER_ID_EXISTS).get().getStatus());
	}

}
