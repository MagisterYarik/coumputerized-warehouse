package telran.analizer.dataContainerAnalazerOpen.service;

import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;

public interface SensorReduser {
	ContainerDemand sensoreReduse(ContainerSensorChanged sensorInfo);
}
