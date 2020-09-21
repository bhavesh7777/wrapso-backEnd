package wrapsto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wrapsto.models.Orders;

import java.util.*;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    @Query(value = "SELECT * FROM Wrapsto.orders where mobile_number=:mobileNumber and status='PAID'",nativeQuery = true)
    List<Orders> ordersWithPaidStatus(String mobileNumber);

    @Query(value = "SELECT * FROM Wrapsto.orders where mobile_number=:mobileNumber and status='ADDED'",nativeQuery = true)
    List<Orders> ordersWithAddedStatus(String mobileNumber);

    Orders findByOrderId(int orderId);
}
