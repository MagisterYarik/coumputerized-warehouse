package telran.microservices.analyzer.close;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.analyzer.close.proxy.OrderDataProxy;

@SpringBootTest
public class ContainerAnalyzerCloseTest {
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	private String producerBindingName = "orderAnalyzeCloseProducer-out-0";
	private String consumerBindingName = "orderAnalyzeCloseConsumer-in-0";
	private static short service_id = 20;
		
	@MockBean
	private OrderDataProxy orderDataProxy;

	private static final long CONTAINER_ID_SENSOR_PREV_MORE = 1000;
	private static final ContainerSensorChanged CONTAINER_SENSOR_PREV_MORE = new ContainerSensorChanged(CONTAINER_ID_SENSOR_PREV_MORE, 0.9, 0.9);
	private static final long CONTAINER_ID_SENSOR_CURRENT_LESS  = 2000;
	private static final ContainerSensorChanged CONTAINER_SENSOR_CURRENT_LESS = new ContainerSensorChanged(CONTAINER_ID_SENSOR_CURRENT_LESS, 0.4, 0.4);
	private static final long CONTAINER_ID_ORDER_NONE = 3000;
	private static final ContainerSensorChanged CONTAINER_ORDER_NONE = new ContainerSensorChanged(CONTAINER_ID_ORDER_NONE, 0.9, 0.4);
	private static final long CONTAINER_ID_ORDER_EXISTS = 4000;
	private static final ContainerSensorChanged CONTAINER_ORDER_EXISTS = new ContainerSensorChanged(CONTAINER_ID_ORDER_EXISTS, 0.9, 0.4);
	private static final OrderDataHeader EXISTING_ORDER_DATA1 = new OrderDataHeader(80085, (char)1, CONTAINER_ID_ORDER_EXISTS, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_DATA2 = new OrderDataHeader(575, (char)1, CONTAINER_ID_ORDER_EXISTS, 1518, 35.5, LocalDate.now(), LocalDate.now().plusWeeks(2));
	private static final OrderRequestClose EXISTING_ORDER_REQUEST1 = new OrderRequestClose(80085, service_id);
	private static final OrderRequestClose EXISTING_ORDER_REQUEST2 = new OrderRequestClose(575, service_id);
	
	private static HashMap<Long, List<OrderDataHeader> > OrderMap = new HashMap<>();
	private static HashMap<Long, List<OrderRequestClose> > RequestMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA1);
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA2);
		
		RequestMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		RequestMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_REQUEST1);
		RequestMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_REQUEST2);
	}
	
	@BeforeEach
	void setUpEach() {
		consumer.clear(producerBindingName);
	}
	
	@Test
	void prevMoreTest() {
		when(orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_ORDER_EXISTS))
		.thenReturn(null);

		producer.send(new GenericMessage<ContainerSensorChanged>(CONTAINER_SENSOR_PREV_MORE), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNull(message);
	}
	
	@Test
	void currentLessTest() {
		when(orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_SENSOR_CURRENT_LESS))
		.thenReturn(null);

		producer.send(new GenericMessage<ContainerSensorChanged>(CONTAINER_SENSOR_CURRENT_LESS), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNull(message);	
	}
	
	@Test
	void orderNoneTest() {
		when(orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_ORDER_NONE))
		.thenReturn(null);

		producer.send(new GenericMessage<ContainerSensorChanged>(CONTAINER_ORDER_NONE), consumerBindingName );
		Message<byte[]> message = consumer.receive(100, producerBindingName );
		assertNull(message);	
	}
	

	@Test
	void orderExistsTest() throws Exception {
		when(orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_ORDER_EXISTS))
		.thenReturn(OrderMap.get(CONTAINER_ID_ORDER_EXISTS));
		ObjectMapper mapper = new ObjectMapper();

		producer.send(new GenericMessage<ContainerSensorChanged>(CONTAINER_ORDER_EXISTS), consumerBindingName );
		
		Message<byte[]> message0 = consumer.receive(100, producerBindingName );
		assertNotNull(message0);
		assertEquals(RequestMap.get(CONTAINER_ID_ORDER_EXISTS).get(0), mapper.readValue(message0.getPayload(), OrderRequestClose.class));
		
		Message<byte[]> message1 = consumer.receive(100, producerBindingName );
		assertNotNull(message1);	
		assertEquals(RequestMap.get(CONTAINER_ID_ORDER_EXISTS).get(1), mapper.readValue(message1.getPayload(), OrderRequestClose.class));	
	}

}
