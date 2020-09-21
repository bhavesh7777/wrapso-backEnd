package wrapsto.service;

import org.springframework.http.ResponseEntity;
import wrapsto.dto.CartWithTotalAmountDTO;
import wrapsto.dto.CheckoutDTO;
import wrapsto.models.Orders;

import java.util.*;

public interface OrdersService {

    ResponseEntity<String> checkoutStatus(CheckoutDTO checkoutDTO);
    CartWithTotalAmountDTO viewCartItem();
    void removeCartItem(int orderId);
    ResponseEntity<String> addCartItem(String userEmail, int foodId);
    ResponseEntity<List<Orders>> ordersWithPaidStatus(String mobileNumber);
}
