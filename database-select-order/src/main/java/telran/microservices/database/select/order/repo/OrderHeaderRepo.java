package telran.microservices.database.select.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.coumputerizedWarehouse.entity.OrderHeader;

public interface OrderHeaderRepo extends JpaRepository<OrderHeader, Long>{
	
	@Query("select ord from OrderDataHeader ord where ord.containerId = :id and ord.status < 21")
	List<OrderHeader> getOpenByContainerId(@Param("id") long id);

}
