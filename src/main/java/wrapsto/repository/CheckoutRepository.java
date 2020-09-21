package wrapsto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wrapsto.models.CheckoutDetails;

@Repository
public interface CheckoutRepository extends JpaRepository<CheckoutDetails,Integer> {

}
