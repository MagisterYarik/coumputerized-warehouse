package telran.microservices.database.create.order.service;

import telran.coumputerizedWarehouse.dto.OrderRequestNew;

public interface CreateOrder {

	void createNewOrder(OrderRequestNew request);

}
