package telran.microservices.analyzer.close.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.OrderDataHeader;
import telran.microservices.analyzer.close.service.ContainerAnalyzeCloseImpl;

@Service
public class OrderDataProxyImpl implements OrderDataProxy {
	@Autowired
	RestTemplate rest;
	@Value("${services.url.getOrderData: 'http://localhost:8080'}")
	String url;
	@Value("${services.command.getOrderDataByContainerId: '/getByContainerId/'}")
	String command;
	ParameterizedTypeReference<List<OrderDataHeader>> classRef = new ParameterizedTypeReference<List<OrderDataHeader>>(){};

	@Override
	public List<OrderDataHeader> openOrderDataByContainerId(long container_id) {
		ResponseEntity<List<OrderDataHeader>> response = rest.exchange(url+command+container_id, HttpMethod.GET, null, classRef);
		if (response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		else {
			return null;
		}
	}

}
