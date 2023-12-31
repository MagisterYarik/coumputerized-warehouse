package telran.microservices.database.create.order.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.coumputerizedWarehouse.entity.OrderHeader;

public interface OrderHeaderRepo extends JpaRepository<OrderHeader, Long>{

}
