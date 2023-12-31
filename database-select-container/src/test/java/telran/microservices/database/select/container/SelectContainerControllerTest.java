package telran.microservices.database.select.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ProductData;
import telran.microservices.database.select.container.service.SelectContainer;

@WebMvcTest
public class SelectContainerControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	@MockBean
	SelectContainer selectContainer;
	@Autowired
	ObjectMapper objectMapper;
	
	private static final long CONTAINER_ID_EXISTS = 1000;
	private static final long CONTAINER_ID_NONE = 2200;
	private static final ProductData PRODUCT_DATA = new ProductData(500, "Potato", "kg", 20, false);
	private static final ContainerData EXISTING_DATA = new ContainerData(CONTAINER_ID_EXISTS, PRODUCT_DATA, 5000);
	
	private static HashMap<Long, ContainerData > OrderMap = new HashMap<>();
	
	private static final String ERROR_MESSAGE = "No container for id " + CONTAINER_ID_NONE;
	
	@BeforeAll
	static void setUpAll() {
		OrderMap.put(CONTAINER_ID_EXISTS, EXISTING_DATA);
	}
	
	@Test
	void dataExistTest() throws Exception{
		when(selectContainer.getContainerById(CONTAINER_ID_EXISTS)).thenReturn(OrderMap.get(CONTAINER_ID_EXISTS));
		
		String jsonOrderData = mockMvc.perform(get("http://localhost:8080/getContainerById/" + CONTAINER_ID_EXISTS))
		.andDo(print()).andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
		String jsonExpected = objectMapper.writeValueAsString(OrderMap.get(CONTAINER_ID_EXISTS));
		assertEquals(jsonExpected, jsonOrderData);
		
	}
	
	@Test
	void dataNotExistTest() throws Exception {
		when(selectContainer.getContainerById(CONTAINER_ID_NONE)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));
		String response = mockMvc.perform(get("http://localhost:8080/getContainerById/" + CONTAINER_ID_NONE))
				.andDo(print()).andExpect(status().isNotFound()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(ERROR_MESSAGE, response);
		
	}

}
