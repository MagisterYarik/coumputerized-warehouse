package telran.microservices.database.create.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.coumputerizedWarehouse.entity.OrderHeader;

public interface OrderTestCheckRepo extends JpaRepository<OrderHeader, Long>{
	
	List<OrderHeader> findByContainerId(long id);

}
