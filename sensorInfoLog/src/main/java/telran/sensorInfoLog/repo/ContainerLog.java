package telran.sensorInfoLog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.sensorInfoLog.entities.ContainerInfo;

public interface ContainerLog extends JpaRepository<ContainerInfo, Long>{

}
