package telran.microservices.database.close.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.OrderRequestClose;
import telran.coumputerizedWarehouse.entity.OrderHeader;
import telran.microservices.database.close.order.repo.OrderHeaderRepo;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CloseOrderImpl implements CloseOrder {
	@Value("${database.order.status.closed:23}")
	private short statusClosed;
	@Value("${database.order.status.treshold.close:13}")
	private short closeThreshold;
	private final OrderHeaderRepo orderRepo;

	@Override
	public void closeOnRequest(OrderRequestClose request) {
		Optional<OrderHeader> orderToClose = orderRepo.findById(request.orderId());
		if (orderToClose.isEmpty()) 
			throw new IllegalArgumentException("Order "+request.orderId()+" not found");
		if (orderToClose.get().getStatus() > closeThreshold) 
			throw new IllegalArgumentException("Order "+request.orderId()+" has status "+orderToClose.get().getStatus()+" and cannot be closed");
		orderToClose.get().setStatus(statusClosed);
		orderRepo.save(orderToClose.get());
		log.debug("Order {} closed by request from source {}", request.orderId(), request.requestSource());

	}

}
