package wrapsto.dto;

import java.util.*;

public class CartWithTotalAmountDTO {

    List<CartItemsDto> cartItemsDto;
    int totalAmount;

    public List<CartItemsDto> getCartItemsDto() {
        return cartItemsDto;
    }

    public void setCartItemsDto(List<CartItemsDto> cartItemsDto) {
        this.cartItemsDto = cartItemsDto;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
