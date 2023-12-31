package telran.microservices.database.select.container.service;

import telran.coumputerizedWarehouse.dto.ContainerData;

public interface SelectContainer {

	ContainerData getContainerById(long id);

}
