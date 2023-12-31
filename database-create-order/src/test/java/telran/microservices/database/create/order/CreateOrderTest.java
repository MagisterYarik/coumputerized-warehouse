package telran.microservices.database.create.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.messaging.support.GenericMessage;

import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.coumputerizedWarehouse.entity.OrderHeader;

@SpringBootTest
public class CreateOrderTest {
	@Autowired
	InputDestination producer;
	private String consumerBindingName = "createOrderConsumer-in-0";
	
	@Autowired
	private OrderTestCheckRepo orderRepo;
	private final short statusNew = 1;
	private final short delay = 2;
	
	private final long TEST_CONTAINER_ID = 1000;
	private final OrderRequestNew TEST_REQUEST = new OrderRequestNew(TEST_CONTAINER_ID, 700, 73.0, (short)30);
	private final OrderHeader TEST_ORDER = new OrderHeader(statusNew, TEST_REQUEST.containerId(), TEST_REQUEST.productId(), TEST_REQUEST.demandUnits(), LocalDate.now(), LocalDate.now().plusDays(delay));
	
	@Test
	void createNewOrderTest() {
		producer.send(new GenericMessage<OrderRequestNew>(TEST_REQUEST), consumerBindingName );
		List<OrderHeader> orderList = orderRepo.findByContainerId(TEST_CONTAINER_ID);
		assertEquals(1, orderList.size());
		OrderHeader order = orderList.get(0);
		assertNotNull(order.getOrderId());
		assertEquals(TEST_ORDER.getStatus(), order.getStatus());
		assertEquals(TEST_ORDER.getContainerId(), order.getContainerId());
		assertEquals(TEST_ORDER.getProductId(), order.getProductId());
		assertEquals(TEST_ORDER.getDemand(), order.getDemand());
		assertEquals(TEST_ORDER.getOrderDate(), order.getOrderDate());
		assertEquals(TEST_ORDER.getDeliveryDate(), order.getDeliveryDate());
	}

}
