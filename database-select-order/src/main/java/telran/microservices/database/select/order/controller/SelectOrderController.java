package telran.microservices.database.select.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.database.select.order.repo.service.SelectOrder;

@RestController
@RequiredArgsConstructor
public class SelectOrderController {
	@Autowired
	final SelectOrder selectOrder;
	
	@GetMapping("getByContainerId/{id}")
	ResponseEntity<?> getOpenOrdersByContainerId(@PathVariable long id) {
		ResponseEntity<?> result = null;
		try {
			List<OrderDataHeader> orderList = selectOrder.getOpenOrdersByContainerId(id);
			result = new ResponseEntity<List<OrderDataHeader>>(orderList, HttpStatus.OK);
		} catch (Exception e) {
			result = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return result;
	}

}
