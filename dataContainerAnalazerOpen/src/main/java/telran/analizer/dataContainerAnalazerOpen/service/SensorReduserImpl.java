package telran.analizer.dataContainerAnalazerOpen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.analizer.dataContainerAnalazerOpen.entitise.ContainerPriveuseState;
import telran.analizer.dataContainerAnalazerOpen.repo.ContainerSensorRepo;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensor;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorReduserImpl implements SensorReduser {

	final ContainerSensorRepo privState;
	@Value("${app.limit:0.5}")
	double limit;

	@Override
	public ContainerDemand sensoreReduse(ContainerSensor sensorInfo) {
		long id = sensorInfo.containerId();

		ContainerPriveuseState lastSensorInfo = privState.findById(id).orElse(null);
		if (privState == null)
			log.debug("container {} not found in Redis", id);
		ContainerPriveuseState newSensorInfo = new ContainerPriveuseState(id, sensorInfo.currentVolume());
		privState.save(newSensorInfo);
		log.trace("Redis updated for container {}",id);

		ContainerDemand res = null;
		if (newSensorInfo.getCurrentVolume() <= limit
				&& (lastSensorInfo == null || lastSensorInfo.getCurrentVolume() > limit)) {
			res = new ContainerDemand(newSensorInfo.getContainerId(), 1 - newSensorInfo.getCurrentVolume());
			log.debug("Create order demand {} for container {}", res, id);

		}else
			log.trace("no order demand for container {}", id);
		
		return res;

	}

}
