package telran.analizer.dataContainerAnalazerOpen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.analizer.dataContainerAnalazerOpen.dto.ContainerDemand;
import telran.analizer.dataContainerAnalazerOpen.dto.ContainerSensor;
import telran.analizer.dataContainerAnalazerOpen.entitise.ContainerPriveuseState;
import telran.analizer.dataContainerAnalazerOpen.repo.ContainerSensorRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorReduserImpl implements SensorReduser {
	@Autowired
	ContainerSensorRepo privState;
	@Value("${app.limit:0.5}")
	double limit;

	@Override
	public ContainerDemand sensoreReduse(ContainerSensor sensorInfo) {
		ContainerPriveuseState lastSensorInfo = privState.findById(sensorInfo.containerId()).orElse(null);
		ContainerPriveuseState newSensorInfo = new ContainerPriveuseState(sensorInfo.containerId(),
				sensorInfo.currentVolume());
		privState.save(newSensorInfo);
		if (newSensorInfo.getCurrentVolume() > limit)
			return null;
		else if (lastSensorInfo == null&& newSensorInfo.getCurrentVolume() <= limit) 
			return new ContainerDemand(newSensorInfo.getContainerId(), 1 - newSensorInfo.getCurrentVolume());
		else if(lastSensorInfo!=null&& lastSensorInfo.getCurrentVolume()>limit&& newSensorInfo.getCurrentVolume() <= limit)
			return new ContainerDemand(newSensorInfo.getContainerId(), 1 - newSensorInfo.getCurrentVolume());
		else 
			return null;
	}

}
