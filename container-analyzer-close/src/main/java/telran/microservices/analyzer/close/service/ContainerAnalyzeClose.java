package telran.microservices.analyzer.close.service;

import java.util.List;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;

public interface ContainerAnalyzeClose {
	List<OrderRequestClose> sensorDataAnalyzeClose(ContainerSensorChanged sensorData);
}
