package telran.analizer.dataContainerAnalazerOpen;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import telran.analizer.dataContainerAnalazerOpen.service.SensorReduser;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DataContainerAnalazerOpenAppl {
	
	final StreamBridge streamBridge;
	@Value("${app.sensor.producer.binding.name:containerInfoProducer-out-0}")
	String bindingName;
	
	final SensorReduser sensorReduser;
	public static void main(String[] args) {
		SpringApplication.run(DataContainerAnalazerOpenAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerSensorChanged> containerInfoConsumer(){
		return this::analize;
		
	}
	void analize(ContainerSensorChanged sensorData) {
		log.trace("received sensor Data: {}", sensorData);
		ContainerDemand res=sensorReduser.sensoreReduse(sensorData);
		if(res!=null) {
			streamBridge.send(bindingName,res);
			log.debug("order demand {} has been sent to {}", res, bindingName);
		}
	}

}
