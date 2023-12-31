package telran.microservices.order.request.service;

import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;

public interface CreateOrderRequestNew {
	OrderRequestNew createRequestByDemand(ContainerDemand request_data);

}
