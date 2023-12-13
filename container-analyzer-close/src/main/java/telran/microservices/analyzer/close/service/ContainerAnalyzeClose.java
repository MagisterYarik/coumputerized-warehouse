package telran.microservices.analyzer.close.service;

import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;

public interface ContainerAnalyzeClose {
	OrderRequestClose SensorDataAnalyzeClose(ContainerSensor sensorData);
}
