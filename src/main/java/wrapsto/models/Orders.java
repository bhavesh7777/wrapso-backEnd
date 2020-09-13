package wrapsto.models;

import javax.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "mobile_number",referencedColumnName = "mobileNumber")
    private Users mobileNumber;

    @ManyToOne
    @JoinColumn(name = "food_item_id", referencedColumnName = "foodId")
    private FoodItems foodItemId;

    @Column(name = "Status")
    private Status status;

    public enum Status {
    ADDED
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Users getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Users mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public FoodItems getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(FoodItems foodItemId) {
        this.foodItemId = foodItemId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
