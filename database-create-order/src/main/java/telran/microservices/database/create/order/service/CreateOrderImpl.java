package telran.microservices.database.create.order.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.OrderRequestNew;
import telran.coumputerizedWarehouse.entity.OrderHeader;
import telran.microservices.database.create.order.repo.OrderHeaderRepo;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CreateOrderImpl implements CreateOrder {
	@Value("${database.order.status.new:1}")
	private short statusNew;
	@Value("${database.order.delay:2}")
	private short delay;
	private final OrderHeaderRepo orderRepo;

	@Override
	public void createNewOrder(OrderRequestNew request) {
		OrderHeader orderNew = new OrderHeader(statusNew, request.containerId(), request.productId(), request.demandUnits(), LocalDate.now(), LocalDate.now().plusDays(delay));
		orderRepo.save(orderNew);
		log.debug("New order for container {} demanding {} units created", request.containerId(), request.demandUnits());

	}

}
