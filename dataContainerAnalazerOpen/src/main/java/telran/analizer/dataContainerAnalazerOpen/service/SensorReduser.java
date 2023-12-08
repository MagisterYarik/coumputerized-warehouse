package telran.analizer.dataContainerAnalazerOpen.service;

import telran.analizer.dataContainerAnalazerOpen.dto.ContainerDemand;
import telran.analizer.dataContainerAnalazerOpen.dto.ContainerSensor;

public interface SensorReduser {
	ContainerDemand sensoreReduse(ContainerSensor sensorInfo);
}
