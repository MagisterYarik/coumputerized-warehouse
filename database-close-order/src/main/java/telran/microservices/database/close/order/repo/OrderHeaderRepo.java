package telran.microservices.database.close.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.coumputerizedWarehouse.entity.OrderHeader;

public interface OrderHeaderRepo extends JpaRepository<OrderHeader, Long>{

}
