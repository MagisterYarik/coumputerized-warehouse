package telran.analizer.DataContainerAnalazerChange.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.analizer.DataContainerAnalazerChange.entitise.ContainerPriveuseState;
import telran.analizer.DataContainerAnalazerChange.repo.ContainerSensorRepo;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorReduserImpl implements SensorReduser {

	final ContainerSensorRepo privState;
	@Value("${app.limit:0.5}")
	double limit;

	@Override
	public ContainerSensorChanged sensoreReduse(ContainerSensor sensorInfo) {
		long id = sensorInfo.containerId();

		ContainerPriveuseState lastSensorInfo = privState.findById(id).orElse(null);
		if (lastSensorInfo == null)
			log.debug("container {} not found in Redis", id);
		
		

		ContainerSensorChanged res = null;
		if(lastSensorInfo==null|| lastSensorInfo.getCurrentVolume()!=sensorInfo.currentVolume())	{	
			ContainerPriveuseState newSensorInfo = new ContainerPriveuseState(id, sensorInfo.currentVolume());
			privState.save(newSensorInfo);
			log.trace("Redis updated for container {}", id);
			res=new ContainerSensorChanged(id, sensorInfo.currentVolume(), lastSensorInfo==null?-1:lastSensorInfo.getCurrentVolume());
		
		}
		return res;

	}
}
