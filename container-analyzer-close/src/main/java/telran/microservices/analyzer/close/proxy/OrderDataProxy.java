package telran.microservices.analyzer.close.proxy;

import java.util.List;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;

public interface OrderDataProxy {
	List<OrderDataHeader> openOrderDataByContainerId(long container_id);

}
