package telran.microservices.order.request.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.coumputerizedWarehouse.dto.ContainerData;

@Service
public class ContainerDataProxyImpl implements ContainerDataProxy {
	@Autowired
	RestTemplate rest;
	@Value("${services.url.getContainerData: 'http://localhost:8080'}")
	String url;
	@Value("${services.command.getContainerDataById: '/getContainerById/'}")
	String command;
	
	@Override
	public ContainerData getContainerData(long container_id) {
		ResponseEntity<ContainerData> response = rest.exchange(url+command+container_id, HttpMethod.GET, null, ContainerData.class);
		if (response.getStatusCode() == HttpStatus.OK)
			return response.getBody();
		else {
			return null;
		}
	}

}
