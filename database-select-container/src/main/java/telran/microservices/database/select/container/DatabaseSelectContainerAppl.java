package telran.microservices.database.select.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
@EntityScan(basePackages = {"telran"})
public class DatabaseSelectContainerAppl {
	
	public static void main(String[] args) {
		SpringApplication.run(DatabaseSelectContainerAppl.class, args);

	}

}
