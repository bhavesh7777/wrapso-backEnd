package wrapsto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wrapsto.models.FoodItems;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItems,Integer> {

    @Query(value = "SELECT * FROM Wrapsto.food_items where food_category=:category",nativeQuery = true)
    Page<FoodItems> getItemByCategory(@Param("category") String category, Pageable pageable);

}
