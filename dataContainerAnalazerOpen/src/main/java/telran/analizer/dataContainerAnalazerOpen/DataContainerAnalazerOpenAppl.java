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
import telran.analizer.dataContainerAnalazerOpen.dto.ContainerDemand;
import telran.analizer.dataContainerAnalazerOpen.dto.ContainerSensor;
import telran.analizer.dataContainerAnalazerOpen.service.SensorReduser;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DataContainerAnalazerOpenAppl {
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.sensor.producer.binding.name:containerInfoProducer-out-0}")
	String bindingName;
	@Autowired
	SensorReduser sensorReduser;
	public static void main(String[] args) {
		SpringApplication.run(DataContainerAnalazerOpenAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerSensor> containerInfoConsumer(){
		return this::analize;
		
	}
	void analize(ContainerSensor sensorData) {
		ContainerDemand res=sensorReduser.sensoreReduse(sensorData);
		if(res!=null) {
			streamBridge.send(bindingName,res);
		}
	}

}
