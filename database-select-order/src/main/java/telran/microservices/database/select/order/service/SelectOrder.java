package telran.microservices.database.select.order.service;

import java.util.List;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;

public interface SelectOrder {

	List<OrderDataHeader> getOpenOrdersByContainerId(long id);

}
