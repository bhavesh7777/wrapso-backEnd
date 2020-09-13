package wrapsto.service.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wrapsto.models.FoodItems;
import wrapsto.models.Orders;
import wrapsto.models.Users;
import wrapsto.repository.FoodItemRepository;
import wrapsto.repository.OrdersRepository;
import wrapsto.repository.UserRepository;
import wrapsto.service.OrdersService;

@Service
public class OrdersImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void removeCartItem(int orderId) {
    ordersRepository.deleteById(orderId);
    }

    @Override
    public ResponseEntity<String> addCartItem(String userEmail , int foodId) {
        Orders orders= new Orders();
        FoodItems foodItem= foodItemRepository.getOne(foodId);
        Users user=userRepository.getOne(userEmail);
        orders.setMobileNumber(user);
        orders.setFoodItemId(foodItem);
        orders.setStatus(Orders.Status.ADDED);
        ordersRepository.save(orders);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart");
    }
}
