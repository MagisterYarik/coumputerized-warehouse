package telran.sensorInfoLog.service;

import java.time.LocalDate;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.sensorInfoLog.entities.ContainerInfo;
import telran.sensorInfoLog.repo.ContainerLog;

@Configuration
@Service
@Slf4j
public class DBWriter {
	@Autowired
	ContainerLog repo;
	
	@Bean
	Consumer<ContainerSensor> containerInfoConsumer(){
		return this::saveLog;
		
	}
	void saveLog(ContainerSensor sensorData) {
		log.trace("received sensor Data: {}", sensorData);
		repo.save(new ContainerInfo(sensorData.containerId(),LocalDate.now(), sensorData.currentVolume()));
		log.debug("save sensor Data: {}", sensorData);

	}
}
