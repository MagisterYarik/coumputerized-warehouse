package telran.microservices.database.select.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.database.select.order.repo.service.SelectOrder;

@WebMvcTest
public class SelectOrderControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@MockBean
	SelectOrder selectOrder;
	@Autowired
	ObjectMapper objectMapper;
	
	private static final long CONTAINER_ID_ORDER_EXISTS = 1000;
	private static final long CONTAINER_ID_ORDER_NONE = 2200;
	private static final OrderDataHeader EXISTING_ORDER_DATA1 = new OrderDataHeader(80085, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_DATA2 = new OrderDataHeader(575, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 35.5, LocalDate.now(), LocalDate.now().plusWeeks(2));
	
	private static HashMap<Long, List<OrderDataHeader> > OrderMap = new HashMap<>();
	
	private static final String ERROR_MESSAGE = "No open orders for container " + CONTAINER_ID_ORDER_NONE;
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA1);
		OrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_DATA2);
	}
	
	@Test
	void dataExistTest() throws Exception{
		when(selectOrder.getOpenOrdersByContainerId(CONTAINER_ID_ORDER_EXISTS)).thenReturn(OrderMap.get(CONTAINER_ID_ORDER_EXISTS));
		
		String jsonOrderData = mockMvc.perform(get("http://localhost:8080/getByContainerId/" + CONTAINER_ID_ORDER_EXISTS))
		.andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		String jsonExpected = objectMapper.writeValueAsString(OrderMap.get(CONTAINER_ID_ORDER_EXISTS));
		assertEquals(jsonExpected, jsonOrderData);
		
	}
	
	@Test
	void dataNotExistTest() throws Exception {
		when(selectOrder.getOpenOrdersByContainerId(CONTAINER_ID_ORDER_NONE)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));
		String response = mockMvc.perform(get("http://localhost:8080/getByContainerId/" + CONTAINER_ID_ORDER_NONE))
				.andDo(print()).andExpect(status().isNotFound()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(ERROR_MESSAGE, response);
		
	}

}
