package telran.analizer.DataContainerAnalazerChange.service;

import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;

public interface SensorReduser {
	ContainerSensorChanged sensoreReduse(ContainerSensor sensorInfo);
}
