package telran.microservices.database.select.container.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.coumputerizedWarehouse.entity.Container;

public interface ContainerRepo extends JpaRepository<Container, Long>{
	
	Container findByContainerId(long id);

}
