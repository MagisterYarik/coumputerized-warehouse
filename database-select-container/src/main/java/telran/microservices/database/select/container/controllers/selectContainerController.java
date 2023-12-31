package telran.microservices.database.select.container.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import telran.coumputerizedWarehouse.dto.ContainerData;
import telran.microservices.database.select.container.service.SelectContainer;

@RestController
public class selectContainerController {
	@Autowired
	private SelectContainer selectContainer;
	
	@GetMapping("getContainerById/{id}")
	ResponseEntity<?> getContainerById(@PathVariable long id) {
		ResponseEntity<?> result = null;
		try {
			ContainerData container = selectContainer.getContainerById(id);
			result = new ResponseEntity<ContainerData>(container, HttpStatus.OK);
		} catch (Exception e) {
			result = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return result;
	}

}
