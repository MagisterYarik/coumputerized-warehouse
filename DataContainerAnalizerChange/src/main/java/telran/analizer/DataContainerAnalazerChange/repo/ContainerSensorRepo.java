package telran.analizer.DataContainerAnalazerChange.repo;

import org.springframework.data.repository.CrudRepository;

import telran.analizer.DataContainerAnalazerChange.entitise.ContainerPriveuseState;


public interface ContainerSensorRepo extends CrudRepository<ContainerPriveuseState, Long> {

}
