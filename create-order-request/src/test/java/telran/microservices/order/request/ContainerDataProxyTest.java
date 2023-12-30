package telran.microservices.order.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ProductData;
import telran.microservices.order.request.proxy.ContainerDataProxy;

@SpringBootTest
public class ContainerDataProxyTest {
	@Autowired
	private ContainerDataProxy containerDataProxy;
	
	@MockBean
	private RestTemplate restTemplate;
	@Value("${services.url.getContainerData: 'http://localhost:8080'}")
	String url;
	@Value("${services.command.getContainerDataById: '/getContainerById/'}")
	private String command;
	
	private static final long CONTAINER_ID_EXISTS = 1000;
	private static final long CONTAINER_ID_NONE = 2200;
	private static final ProductData PRODUCT_DATA = new ProductData(500, "Potato", "kg", 20, false);
	private static final ContainerData EXISTING_DATA = new ContainerData(CONTAINER_ID_EXISTS, PRODUCT_DATA, 5000);
	
	private static HashMap<Long, ContainerData > OrderMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_EXISTS, EXISTING_DATA);
	}
	
	@Test
	void containerNoneTest() {
		when(restTemplate.exchange(url+command+CONTAINER_ID_NONE, HttpMethod.GET, null, ContainerData.class))
		.thenReturn(new ResponseEntity("Error", HttpStatus.NOT_FOUND));
		
		assertNull(containerDataProxy.getContainerData(CONTAINER_ID_NONE));		
	}
	
	@Test
	void containerExistsTest() {
		when(restTemplate.exchange(url+command+CONTAINER_ID_EXISTS, HttpMethod.GET, null, ContainerData.class))
		.thenReturn(new ResponseEntity(OrderMap.get(CONTAINER_ID_EXISTS), HttpStatus.OK));
		
		assertEquals(OrderMap.get(CONTAINER_ID_EXISTS), containerDataProxy.getContainerData(CONTAINER_ID_EXISTS));	
		
	}

}
