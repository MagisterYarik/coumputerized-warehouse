package telran.analizer.dataContainerAnalazerOpen.service;

import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensor;

public interface SensorReduser {
	ContainerDemand sensoreReduse(ContainerSensor sensorInfo);
}
