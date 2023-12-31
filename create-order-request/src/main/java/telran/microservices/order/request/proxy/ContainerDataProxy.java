package telran.microservices.order.request.proxy;

import telran.coumputerizedWarehouse.dto.ContainerData;

public interface ContainerDataProxy {
	ContainerData getContainerData(long container_id);

}
