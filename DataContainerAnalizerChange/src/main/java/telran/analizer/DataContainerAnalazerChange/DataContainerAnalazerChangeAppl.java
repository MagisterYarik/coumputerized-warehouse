package telran.analizer.DataContainerAnalazerChange;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.analizer.DataContainerAnalazerChange.service.SensorReduser;
import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;
@SpringBootApplication
	@RequiredArgsConstructor
	@Slf4j
public class DataContainerAnalazerChangeAppl {
	final StreamBridge streamBridge;
	@Value("${app.sensor.producer.binding.name:containerInfoChangedProducer-out-0}")
	String bindingName;
	
	final SensorReduser sensorReduser;
	public static void main(String[] args) {
		SpringApplication.run(DataContainerAnalazerChangeAppl.class, args);

	}
	
	@Bean
	Consumer<ContainerSensor> containerInfoConsumer(){
		return this::analize;
		
	}
	void analize(ContainerSensor sensorData) {
		log.trace("received sensor Data: {}", sensorData);
		ContainerSensorChanged res=sensorReduser.sensoreReduse(sensorData);
		if(res!=null) {
			streamBridge.send(bindingName,res);
			log.debug("Container Changed data {} has been sent to {}", res, bindingName);
		}
	}
}
