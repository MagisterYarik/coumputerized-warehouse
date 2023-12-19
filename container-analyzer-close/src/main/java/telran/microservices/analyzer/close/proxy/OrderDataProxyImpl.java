package telran.microservices.analyzer.close.proxy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.coumputerizedWarehouse.dto.OrderDataHeader;

@Service
public class OrderDataProxyImpl implements OrderDataProxy {
	@Autowired
	RestTemplate rest;
	@Value("#{${ServiceUrlMap}['getOrderData'] ?: 'http://localhost:8080'")
	String url;
	@Value("#{${ServiceCommandMap}['getOrderDataByContainerId'] ?: '/getByContainerId'")
	String command;

	@Override
	public List<OrderDataHeader> OpenOrderDataByContainerId(long container_id) {
		ResponseEntity<List<OrderDataHeader>> response = rest.exchange(url+command+container_id, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<OrderDataHeader>>(){});
		return response.getBody();
	}

}
