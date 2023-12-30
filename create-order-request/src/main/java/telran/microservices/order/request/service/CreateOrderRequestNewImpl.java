package telran.microservices.order.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.microservices.order.request.proxy.ContainerDataProxy;

@Service
@Slf4j
public class CreateOrderRequestNewImpl implements CreateOrderRequestNew {
	@Value("${services.id.new:30}")
	private short serviceId;
	@Autowired
	private ContainerDataProxy containerDataProxy;

	@Override
	public OrderRequestNew createRequestByDemand(ContainerDemand request_data) {
		ContainerData container = containerDataProxy.getContainerData(request_data.containerId());
		if (container == null) {
			log.debug("Container {} not found", request_data.containerId());
			return null;
		}
		double demandUnit = container.productCapacity()*request_data.demandVolume();
		if(container.product().discrete()) {
			demandUnit = Math.floor(demandUnit);
			log.trace("Discrete demand for product {} in container {}", container.product().productId(), request_data.containerId());
		}
		log.debug("Generated request for order of {} {} of {} to container{}", demandUnit, container.product().units(), container.product().productName(), request_data.containerId());
		return new OrderRequestNew(request_data.containerId(), container.product().productId(), demandUnit, serviceId);
	}

}
