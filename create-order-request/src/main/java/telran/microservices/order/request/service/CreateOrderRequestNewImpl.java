package telran.microservices.order.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.microservices.order.request.proxy.ContainerDataProxy;

@Service
public class CreateOrderRequestNewImpl implements CreateOrderRequestNew {
	@Value("${services.id.new:30}")
	private short service_id;
	@Autowired
	private ContainerDataProxy containerDataProxy;

	@Override
	public OrderRequestNew createRequestByDemand(ContainerDemand request_data) {
		ContainerData container = containerDataProxy.getContainerData(request_data.containerId());
		if (container == null)
			return null;
		double demandUnit = container.product_capacity()*request_data.demandVolume();
		if(container.product().discrete())
			demandUnit = Math.floor(demandUnit);
		return new OrderRequestNew(request_data.containerId(), container.product().product_id(), demandUnit, service_id);
	}

}
