package telran.microservices.analyzer.close.repo;

import org.springframework.data.repository.CrudRepository;

import telran.microservices.analyzer.close.entity.SensorData;

public interface SensorDataRepo extends CrudRepository<SensorData, Long>{

}
