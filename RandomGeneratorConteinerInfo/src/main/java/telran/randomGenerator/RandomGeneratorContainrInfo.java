package telran.randomGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RandomGeneratorContainrInfo {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext cac =SpringApplication.run(RandomGeneratorContainrInfo.class, args);
		Thread.sleep(30000);
		cac.close();
	}

}
