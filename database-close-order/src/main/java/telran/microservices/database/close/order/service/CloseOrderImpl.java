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
	private final OrderHeaderRepo orderRepo;

	@Override
	public void closeOnRequest(OrderRequestClose request) {
		Optional<OrderHeader> orderToClose = orderRepo.findById(request.orderId());
		if (orderToClose.isEmpty()) 
			throw new IllegalArgumentException("Order "+request.orderId()+" not found");
		//Stump, for actual impl need additional status checks
		if (orderToClose.get().getStatus() == statusClosed) {
			log.debug("Order {} was already closed", request.orderId());
			return;
		}			
		orderToClose.get().setStatus(statusClosed);
		orderRepo.save(orderToClose.get());
		log.debug("Order {} closed by request from source {}", request.orderId(), request.requestSource());

	}

}
