package telran.microservices.analyzer.close;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;

@SpringBootTest
public class OrderDataProxyTest {
	@MockBean
	RestTemplate restTemplate;
	
	private static final long CONTAINER_ID_ORDER_EXISTS = 100;
	private static final long CONTAINER_ID_ORDER_NONE = 200;
	private static final OrderDataHeader EXISTING_ORDER_DATA = new OrderDataHeader(80085, (char)1, 100, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	
	static HashMap<Long, List<OrderDataHeader> > OrderMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA);
	}
	
	@Test
	void probeNoValuesTest() {
		
	}

}
