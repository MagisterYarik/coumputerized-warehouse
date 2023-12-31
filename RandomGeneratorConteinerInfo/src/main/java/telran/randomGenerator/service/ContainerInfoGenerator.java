package telran.randomGenerator.service;

import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import telran.coumputerizedWarehouse.dto.ContainerSensor;

@Configuration
public class ContainerInfoGenerator {

    @Bean
    Supplier<ContainerSensor> sendContainerInfo(){
		return ()->{
			
			ContainerSensor res=getRandomContainerInfo();
			try {
				return res;
			} catch (Exception e) {
				return null;
			}
			
		};
		
	}

	private ContainerSensor getRandomContainerInfo() {
		int id=(int)(Math.random()*50)+1;
		double value=Math.random();
		ContainerSensor res=new ContainerSensor(id, value);
		return res;
	}
}
