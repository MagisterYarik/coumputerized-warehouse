package telran.microservices.analyzer.close.proxy;

import java.util.List;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;

public interface OrderDataProxy {
	List<OrderDataHeader> OpenOrderDataByContainerId(long container_id);

}
