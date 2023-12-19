package telran.microservices.analyzer.close.service;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;

public interface ContainerAnalyzeClose {
	OrderRequestClose SensorDataAnalyzeClose(ContainerSensorChanged sensorData);
}
