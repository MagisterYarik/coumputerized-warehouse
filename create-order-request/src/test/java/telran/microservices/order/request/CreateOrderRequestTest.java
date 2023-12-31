package telran.microservices.order.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.coumputerizedWarehouse.dto.ProductData;
import telran.microservices.order.request.proxy.ContainerDataProxy;

@SpringBootTest
public class CreateOrderRequestTest {
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	private String producerBindingName = "createOrderRequestNewProducer-out-0";
	private String consumerBindingName = "createOrderRequestNewConsumer-in-0";
	private static short serviceId = 30;

	@MockBean
	private ContainerDataProxy containerDataProxy;
	
	private static final long CONTAINER_ID_DISCRETE = 1000;
	private static final ContainerDemand CONTAINER_DEMAND_DISCRETE = new ContainerDemand(CONTAINER_ID_DISCRETE, 0.733);
	private static final long CONTAINER_ID_NOT_DISCRETE = 2000;
	private static final ContainerDemand CONTAINER_DEMAND_NOT_DISCRETE = new ContainerDemand(CONTAINER_ID_NOT_DISCRETE, 0.733);
	private static final long CONTAINER_ID_NONE = 2200;
	private static final ContainerDemand CONTAINER_DEMAND_NONE = new ContainerDemand(CONTAINER_ID_NONE, 0.7);
	private static final long PRODUCT_ID_DISCRETE = 700;
	private static final long PRODUCT_ID_NOT_DISCRETE = 500;
	private static final ProductData PRODUCT_DATA_DISCRETE = new ProductData(PRODUCT_ID_DISCRETE, "Chocolate", "box", 2, true);
	private static final ProductData PRODUCT_DATA_NOT_DISCRETE = new ProductData(PRODUCT_ID_NOT_DISCRETE, "Potato", "kg", 20, false);
	private static final ContainerData CONTAINER_DATA_DISCRETE = new ContainerData(CONTAINER_ID_DISCRETE, PRODUCT_DATA_DISCRETE, 100);
	private static final ContainerData CONTAINER_DATA_NOT_DISCRETE = new ContainerData(CONTAINER_ID_NOT_DISCRETE, PRODUCT_DATA_NOT_DISCRETE, 100);
	private static final OrderRequestNew ORDER_REQUEST_DISCRETE = new OrderRequestNew(CONTAINER_ID_DISCRETE, PRODUCT_ID_DISCRETE, 73.0, serviceId);
	private static final OrderRequestNew ORDER_REQUEST_NOT_DISCRETE = new OrderRequestNew(CONTAINER_ID_NOT_DISCRETE, PRODUCT_ID_NOT_DISCRETE, 73.3, serviceId);
	
	private static HashMap<Long, ContainerData > OrderMap = new HashMap<>();
	private static HashMap<Long, OrderRequestNew > RequestMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_DISCRETE, CONTAINER_DATA_DISCRETE);
		OrderMap.put(CONTAINER_ID_NOT_DISCRETE, CONTAINER_DATA_NOT_DISCRETE);
		
		RequestMap.put(CONTAINER_ID_DISCRETE, ORDER_REQUEST_DISCRETE);
		RequestMap.put(CONTAINER_ID_NOT_DISCRETE, ORDER_REQUEST_NOT_DISCRETE);
	}
	
	@BeforeEach
	void setUpEach() {
		consumer.clear(producerBindingName);
	}
	
	@Test
	void orderNoneTest() {
		when(containerDataProxy.getContainerData(CONTAINER_ID_NONE))
		.thenReturn(null);

		producer.send(new GenericMessage<ContainerDemand>(CONTAINER_DEMAND_NONE), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNull(message);	
	}
	
	@Test
	void orderDiscreteTest() throws Exception {
		when(containerDataProxy.getContainerData(CONTAINER_ID_DISCRETE))
		.thenReturn(OrderMap.get(CONTAINER_ID_DISCRETE));
		ObjectMapper mapper = new ObjectMapper();

		producer.send(new GenericMessage<ContainerDemand>(CONTAINER_DEMAND_DISCRETE), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNotNull(message);
		assertEquals(RequestMap.get(CONTAINER_ID_DISCRETE), mapper.readValue(message.getPayload(), OrderRequestNew.class));
	}
	
	@Test
	void orderNotDiscreteTest() throws Exception {
		when(containerDataProxy.getContainerData(CONTAINER_ID_NOT_DISCRETE))
		.thenReturn(OrderMap.get(CONTAINER_ID_NOT_DISCRETE));
		ObjectMapper mapper = new ObjectMapper();

		producer.send(new GenericMessage<ContainerDemand>(CONTAINER_DEMAND_NOT_DISCRETE), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNotNull(message);
		assertEquals(RequestMap.get(CONTAINER_ID_NOT_DISCRETE), mapper.readValue(message.getPayload(), OrderRequestNew.class));
	}

}
