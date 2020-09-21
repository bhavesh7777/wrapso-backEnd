package wrapsto.service.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wrapsto.config.Constants;
import wrapsto.models.FoodItems;
import wrapsto.repository.FoodItemRepository;
import wrapsto.service.FoodItemService;

@Service
public class FoodItemImpl implements FoodItemService {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Override
    public ResponseEntity<String> addFoodItem(FoodItems foodItems) {
        FoodItems items= new FoodItems();
        items.setAmount(foodItems.getAmount());
        items.setFoodCategory(foodItems.foodCategory);
        items.setFoodDescription(foodItems.getFoodDescription());
        items.setFoodName(foodItems.getFoodName());
        items.setImage(foodItems.getImage());
        foodItemRepository.save(items);
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.ITEMS_ADDED);
    }

    @Override
    public Page<FoodItems> getAllPizza(String catrgory,Pageable pageable) {
        return foodItemRepository.getItemByCategory(catrgory,pageable);
    }
}
