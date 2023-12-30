package telran.microservices.database.select.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.database.select.order.service.SelectOrder;

@SpringBootTest
@Sql(scripts = "db-test-script.sql")
public class SelectOrderServiceTest {
	@Autowired
	private SelectOrder selectOrder;
	
	private static final long CONTAINER_ID_ORDER_EXISTS = 1000;
	private static final long CONTAINER_ID_ORDER_ONE_CLOSED = 2000;
	private static final long CONTAINER_ID_ORDER_NONE = 2200;
	private static final OrderDataHeader EXISTING_ORDER_EXISTS_DATA1 = new OrderDataHeader(80085, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 75.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_EXISTS_DATA2 = new OrderDataHeader(575, (short)1, CONTAINER_ID_ORDER_EXISTS, 1518, 35.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_ONE_CLOSED_DATA1 = new OrderDataHeader(7, (short)20, CONTAINER_ID_ORDER_ONE_CLOSED, 1518, 79.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	private static final OrderDataHeader EXISTING_ORDER_ONE_CLOSED_DATA2 = new OrderDataHeader(8, (short)22, CONTAINER_ID_ORDER_ONE_CLOSED, 1518, 91.5, LocalDate.now(), LocalDate.now().plusWeeks(1));
	
	private static HashMap<Long, List<OrderDataHeader> > OpenOrderMap = new HashMap<>();
	
	@BeforeAll
	static void setUpAll() {
		OpenOrderMap.put(CONTAINER_ID_ORDER_EXISTS, new ArrayList<>());
		OpenOrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_EXISTS_DATA1);
		OpenOrderMap.get(CONTAINER_ID_ORDER_EXISTS).add(EXISTING_ORDER_EXISTS_DATA2);
		OpenOrderMap.put(CONTAINER_ID_ORDER_ONE_CLOSED, new ArrayList<>());
		OpenOrderMap.get(CONTAINER_ID_ORDER_ONE_CLOSED).add(EXISTING_ORDER_ONE_CLOSED_DATA1);
	}
	
	@Test
	void ordersExistTest() {
		List<OrderDataHeader> openOrderList = selectOrder.getOpenOrdersByContainerId(CONTAINER_ID_ORDER_EXISTS);
		assertNotNull(openOrderList);
		assertEquals(Set.copyOf(OpenOrderMap.get(CONTAINER_ID_ORDER_EXISTS)), Set.copyOf(openOrderList));
	}
	
	@Test
	void ordersOneClosedTest() {
		List<OrderDataHeader> openOrderList = selectOrder.getOpenOrdersByContainerId(CONTAINER_ID_ORDER_ONE_CLOSED);
		assertNotNull(openOrderList);
		assertEquals(Set.copyOf(OpenOrderMap.get(CONTAINER_ID_ORDER_ONE_CLOSED)), Set.copyOf(openOrderList));
	}
	
	@Test
	void ordersNoneTest() {
		List<OrderDataHeader> openOrderList = selectOrder.getOpenOrdersByContainerId(CONTAINER_ID_ORDER_NONE);
		assertNull(openOrderList);
	}

}
