package wrapsto.service;

import org.springframework.http.ResponseEntity;

public interface OrdersService {

    void removeCartItem(int orderId);
    ResponseEntity<String> addCartItem(String userEmail, int foodId);
}
