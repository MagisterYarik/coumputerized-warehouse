package telran.microservices.analyzer.close.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.microservices.analyzer.close.proxy.OrderDataProxy;

@Service
@Slf4j
public class ContainerAnalyzeCloseImpl implements ContainerAnalyzeClose {
	@Value("${container.limit.close:0.5}")
	private double limit;
	@Value("${services.id.close:20}")
	private short service_id;
	@Autowired
	private OrderDataProxy orderDataProxy;

	@Override
	public List<OrderRequestClose> sensorDataAnalyzeClose(ContainerSensorChanged sensorData) {
		if (sensorData.currentVolume() < limit) {
			log.trace("Current volume {} less than treshold {}", sensorData.currentVolume(), limit);
			return null;
		}
		if (sensorData.prevVolume() >= limit) {
			log.trace("Previous volume {} more than treshold {}", sensorData.prevVolume(), limit);
			return null;
		}
		List<OrderDataHeader> orderList = orderDataProxy.openOrderDataByContainerId(sensorData.containerId());
		if (orderList == null) {
			log.trace("Open orders not found for container {}", sensorData.containerId());
			return null;
		}
		List<OrderRequestClose> requestList = new ArrayList<>();
		orderList.forEach(order -> {
			log.debug("Request to close order {} for container {} generated", order.order_id(), sensorData.containerId());
			requestList.add(new OrderRequestClose(order.order_id(), service_id));
			});
		return requestList;
	}

}
