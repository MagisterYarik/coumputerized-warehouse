package telran.analizer.dataContainerAnalazerOpen.repo;

import org.springframework.data.repository.CrudRepository;

import telran.analizer.dataContainerAnalazerOpen.entitise.ContainerPriveuseState;

public interface ContainerSensorRepo extends CrudRepository<ContainerPriveuseState, Long> {

}
