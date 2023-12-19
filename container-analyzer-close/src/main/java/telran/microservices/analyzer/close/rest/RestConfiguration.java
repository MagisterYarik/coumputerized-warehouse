package telran.microservices.analyzer.close.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfiguration {
	
	@Bean
	public RestTemplate getRestCalculator()
	{
		return new RestTemplate();
	}

}
