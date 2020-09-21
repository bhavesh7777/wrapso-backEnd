package wrapsto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import wrapsto.config.Constants;
import wrapsto.dto.*;
import wrapsto.exceptionhandling.BadRequest;
import wrapsto.models.FoodItems;
import wrapsto.models.Orders;
import wrapsto.security.TokenVerification;
import wrapsto.service.serviceimplements.FoodItemImpl;
import wrapsto.service.serviceimplements.OrdersImpl;
import wrapsto.service.serviceimplements.SendOtpImpl;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(allowCredentials = "true")
public class UserController {

    @Autowired
    SendOtpImpl sendOtp;

    @Autowired
    FoodItemImpl foodItemImpl;

    @Autowired
    OrdersImpl ordersImpl;

    @Autowired
    TokenVerification tokenVerification;

    @PostMapping("otp")
    public ResponseEntity<String> loginSendOtp(@Validated @RequestBody MobileNumberDto mobileNumber, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequest(Constants.MOBILE_NUMBER_IS_MANDATORY);
        }
        return sendOtp.sendNotification(mobileNumber.getMobileNumber());
    }

    @PutMapping("otp")
    public ResponseEntity<String> verifyOtp(HttpServletResponse response, @Validated @RequestBody VerfiyOTPDto verfiyOTPDto) {

        return sendOtp.verifyOTP(response, verfiyOTPDto);
    }

    @PostMapping("food-item")
    public ResponseEntity<String> addItem(@RequestBody FoodItems foodItems) {

        return foodItemImpl.addFoodItem(foodItems);
    }

    @GetMapping("item/{category}")
    public Page<FoodItems> pizza(@PathVariable(value = "category") String category, Pageable pageable) {
        return foodItemImpl.getAllPizza(category.toUpperCase(), pageable);
    }

    @PostMapping("cart/item/{foodId}")
    public ResponseEntity<String> addItemToCart(@PathVariable int foodId) {
        //String userEmail = tokenVerification.extractDataFromToken(token).getSubject();
        return ordersImpl.addCartItem("+917676455551", foodId);
    }

    @DeleteMapping("cart/item/{orderId}")
    public void removeItemFromCart(@PathVariable int orderId) {
        ordersImpl.removeCartItem(orderId);
    }

    @GetMapping("cart/item")
    public ResponseEntity<CartWithTotalAmountDTO> cartItems() {
        return ResponseEntity.ok(ordersImpl.viewCartItem());
    }

    @PutMapping("cart/checkout")
    public ResponseEntity<String> updateCartStatusWithUserData(@RequestBody CheckoutDTO checkoutDTO) {
        return ordersImpl.checkoutStatus(checkoutDTO);
    }

    @GetMapping("orders")
    public ResponseEntity<List<Orders>> getOrdersForUser(){
        return ordersImpl.ordersWithPaidStatus("+917676455551");

    }

}
