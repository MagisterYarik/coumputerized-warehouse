package telran.microservices.database.select.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})
@EntityScan(basePackages = {"telran"})
public class DatabaseSelectOrderAppl {
	public static void main(String[] args) {
		SpringApplication.run(DatabaseSelectOrderAppl.class, args);

	}

}
