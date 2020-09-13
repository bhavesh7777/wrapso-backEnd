package wrapsto.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import wrapsto.models.FoodItems;

public interface FoodItemService {

    ResponseEntity<String> addFoodItem(FoodItems foodItems);

    Page<FoodItems> getAllPizza(String category,Pageable pageable);
}
