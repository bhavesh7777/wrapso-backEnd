package wrapsto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wrapsto.models.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
}
