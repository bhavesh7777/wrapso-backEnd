package wrapsto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wrapsto.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {

}
