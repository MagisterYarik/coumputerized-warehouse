package telran.microservices.database.select.container;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ProductData;
import telran.microservices.database.select.container.service.SelectContainer;

@SpringBootTest
@Sql(scripts = "db-test-script.sql")
public class SelectContainerServiceTest {
	@Autowired
	private SelectContainer selectContainer;
	
	private static final long CONTAINER_ID_EXISTS = 1000;
	private static final long CONTAINER_ID_NONE = 2200;
	private static final ProductData PRODUCT_DATA = new ProductData(500, "Potato", "kg", 20, false);
	private static final ContainerData EXISTING_DATA = new ContainerData(CONTAINER_ID_EXISTS, PRODUCT_DATA, 5000);
	
	private static HashMap<Long, ContainerData > ContainerMap = new HashMap<>();
	
	private static final String ERROR_MESSAGE = "No container for id " + CONTAINER_ID_NONE;
	
	@BeforeAll
	static void setUpAll() {
		ContainerMap.put(CONTAINER_ID_EXISTS, EXISTING_DATA);
	}
	
	@Test
	void ordersExistTest() {
		ContainerData container = selectContainer.getContainerById(CONTAINER_ID_EXISTS);
		assertNotNull(container);
		assertEquals(ContainerMap.get(CONTAINER_ID_EXISTS), container);
	}
	
	@Test
	void ordersNoneTest() {
		ContainerData container = null;
		try {
			container = selectContainer.getContainerById(CONTAINER_ID_NONE);
		} catch (Exception e) {
			assertEquals(ERROR_MESSAGE, e.getMessage());
		}
		assertNull(container);
	}

}
