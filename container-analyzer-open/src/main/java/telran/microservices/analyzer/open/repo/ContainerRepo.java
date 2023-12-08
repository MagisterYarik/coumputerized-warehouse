package telran.microservices.analyzer.open.repo;

import org.springframework.data.repository.CrudRepository;

import telran.microservices.analyzer.open.entity.ContainerData;

public interface ContainerRepo extends CrudRepository<ContainerData, Long> {

}
