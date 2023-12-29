package telran.microservices.analyzer.close;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.analyzer.close.proxy.OrderDataProxy;

@SpringBootTest
public class ContainerAnalyzerCloseTest {
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	private String producerBindingName = "orderAnalyzeCloseProducer-out-0";
	private String consumerBindingName = "orderAnalyzeCloseConsumer-in-0";
	
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
	
	private static HashMap<Long, List<OrderDataHeader> > OrderMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA1);
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA2);
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

}
