package telran.analizer.dataContainerAnalazerOpen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.coumputerizedWarehouse.dto.ContainerDemand;
import telran.coumputerizedWarehouse.dto.ContainerSensor;
import telran.coumputerizedWarehouse.dto.ContainerSensorChanged;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorReduserImpl implements SensorReduser {

	@Value("${app.limit:0.5}")
	double limit;

	@Override
	public ContainerDemand sensoreReduse(ContainerSensorChanged sensorInfo) {

		
		if (sensorInfo.prevVolume()==-1) {
			log.debug("container {} not priveouse data", sensorInfo.containerId());
		
		}
		
		ContainerDemand res = null;
		if (sensorInfo.currentVolume() <= limit
				&& (sensorInfo.prevVolume() == -1 || sensorInfo.prevVolume() > limit)) {
			res = new ContainerDemand(sensorInfo.containerId(), 1 - sensorInfo.currentVolume());
			log.debug("Create order demand {} for container {}", res, sensorInfo.containerId());

		} else
			log.trace("no order demand for container {}", sensorInfo.containerId());

		return res;

	}

}
