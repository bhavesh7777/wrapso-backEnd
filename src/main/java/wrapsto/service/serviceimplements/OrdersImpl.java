package wrapsto.service.serviceimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wrapsto.config.Constants;
import wrapsto.dto.CartItemsDto;
import wrapsto.dto.CartWithTotalAmountDTO;
import wrapsto.dto.CheckoutDTO;
import wrapsto.models.CheckoutDetails;
import wrapsto.models.FoodItems;
import wrapsto.models.Orders;
import wrapsto.models.Users;
import wrapsto.repository.CheckoutRepository;
import wrapsto.repository.FoodItemRepository;
import wrapsto.repository.OrdersRepository;
import wrapsto.repository.UserRepository;
import wrapsto.service.OrdersService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CheckoutRepository checkoutRepository;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(Constants.ITEM_ADDED_TO_CART);
    }

    @Override
    public ResponseEntity<String> checkoutStatus(CheckoutDTO checkoutDTO) {

        CheckoutDetails checkoutDetails= new CheckoutDetails();
        checkoutDetails.setFullName(checkoutDTO.getFullName());
        checkoutDetails.setEmail(checkoutDetails.getEmail());
        checkoutDetails.setAddress(checkoutDTO.getAddress());
        checkoutDetails.setCity(checkoutDTO.getCity());
        checkoutDetails.setPinCode(checkoutDTO.getPinCode());
        checkoutDetails.setState(checkoutDTO.getState());

        for(int i=0;i<checkoutDTO.getOrderId().size();i++){
            Orders orders= ordersRepository.findByOrderId(checkoutDTO.getOrderId().get(i));
            orders.setStatus(Orders.Status.PAID);
            ordersRepository.save(orders);
        }
        checkoutRepository.save(checkoutDetails);
        return null;
    }

    @Override
    public CartWithTotalAmountDTO viewCartItem() {
        int totalAmount=0;
        String mobileNumber="+917676455551";
        CartWithTotalAmountDTO cartWithTotalAmountDTO=new CartWithTotalAmountDTO();
        List<Orders> orders=ordersRepository.ordersWithAddedStatus(mobileNumber);
        List<CartItemsDto> cartItemsDtos = new ArrayList<>();
        for (Orders order : orders) {
            FoodItems foodItems = foodItemRepository.findById(order.getFoodItemId().getFoodId()).orElseThrow();
            CartItemsDto cartItemsDto = new CartItemsDto();
            totalAmount = totalAmount + (foodItems.getAmount());
            cartItemsDto.setAmount(foodItems.getAmount());
            cartItemsDto.setFoodId(foodItems.getFoodId());
            cartItemsDto.setFoodName(foodItems.getFoodName());
            cartItemsDto.setOrderId(order.getOrderId());
            cartItemsDtos.add(cartItemsDto);
        }
       cartWithTotalAmountDTO.setCartItemsDto(cartItemsDtos);
        cartWithTotalAmountDTO.setTotalAmount(totalAmount);
        return cartWithTotalAmountDTO;
    }

    @Override
    public ResponseEntity<List<Orders>> ordersWithPaidStatus(String mobileNumber) {

        return ResponseEntity.ok(ordersRepository.ordersWithPaidStatus(mobileNumber));
    }
}
