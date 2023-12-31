package telran.microservices.analyzer.close;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.analyzer.close.proxy.OrderDataProxy;

@SpringBootTest
public class OrderDataProxyTest {
	@Autowired
	private OrderDataProxy orderDataProxy;
	
	@MockBean
	private RestTemplate restTemplate;
	@Value("${services.url.getOrderData: 'http://localhost:8080'}")
	private String url;
	@Value("${services.command.getOrderDataByContainerId: '/getByContainerId/'}")
	private String command;
	ParameterizedTypeReference<List<OrderDataHeader>> classRef = new ParameterizedTypeReference<List<OrderDataHeader>>(){};
	
	private static final long CONTAINER_ID_ORDER_EXISTS = 1000;
	private static final long CONTAINER_ID_ORDER_NONE = 2200;
	private static final OrderDataHeader EXISTING_ORDER_DATA1 = new OrderDataHeader(80085, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_DATA2 = new OrderDataHeader(575, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 35.5, LocalDate.now(), LocalDate.now().plusWeeks(2));
	
	private static HashMap<Long, List<OrderDataHeader> > OrderMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA1);
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA2);
	}
	
	@Test
	void ordersNoneTest() {
		when(restTemplate.exchange(url+command+CONTAINER_ID_ORDER_NONE, HttpMethod.GET, null, classRef))
		.thenReturn(new ResponseEntity("Error", HttpStatus.NOT_FOUND));
		
		assertNull(orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_ORDER_NONE));		
	}
	
	@Test
	void ordersExistsTest() {
		when(restTemplate.exchange(url+command+CONTAINER_ID_ORDER_EXISTS, HttpMethod.GET, null, classRef))
		.thenReturn(new ResponseEntity(OrderMap.get(CONTAINER_ID_ORDER_EXISTS), HttpStatus.OK));
		
		assertEquals(OrderMap.get(CONTAINER_ID_ORDER_EXISTS), orderDataProxy.openOrderDataByContainerId(CONTAINER_ID_ORDER_EXISTS));	
		
	}

}
