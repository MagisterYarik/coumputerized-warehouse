package telran.microservices.database.select.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.coumputerizedWarehouse.entity.OrderHeader;
import telran.microservices.database.select.order.repo.OrderHeaderRepo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SelectOrderImpl implements SelectOrder {
	final OrderHeaderRepo orderRepo;

	@Override
	public List<OrderDataHeader> getOpenOrdersByContainerId(long id) {
		List<OrderHeader> orderList = orderRepo.getOpenByContainerId(id);
		if (orderList.isEmpty()) {
			return null;
		}
		List<OrderDataHeader> result = new ArrayList<OrderDataHeader>();
		orderList.forEach(order -> result.add(new OrderDataHeader(order)));
		return result;
	}

}
