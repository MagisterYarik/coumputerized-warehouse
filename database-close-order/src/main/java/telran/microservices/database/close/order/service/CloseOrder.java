package telran.microservices.database.close.order.service;

import telran.coumputerizedWarehouse.dto.OrderRequestClose;

public interface CloseOrder {
	void closeOnRequest(OrderRequestClose request);

}
