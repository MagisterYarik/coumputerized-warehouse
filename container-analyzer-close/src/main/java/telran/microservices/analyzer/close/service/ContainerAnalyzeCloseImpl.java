package telran.microservices.analyzer.close.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.analyzer.close.proxy.OrderDataProxy;

public class ContainerAnalyzeCloseImpl implements ContainerAnalyzeClose {
	@Value("${container.limit.close:0.5}")
	double limit;
	@Value("${services.id.close:20}")
	char service_id;
	@Autowired
	OrderDataProxy orderDataProxy;

	@Override
	public List<OrderRequestClose> sensorDataAnalyzeClose(ContainerSensorChanged sensorData) {
		if (sensorData.currentVolume() < limit || sensorData.prevVolume() >= limit) 
			return null;
		List<OrderDataHeader> orderList = orderDataProxy.openOrderDataByContainerId(sensorData.containerId());
		if (orderList == null)
			return null;
		List<OrderRequestClose> requestList = new ArrayList<>();
		orderList.forEach(order -> requestList.add(new OrderRequestClose(order.order_id(), service_id)));
		return requestList;
	}

}
