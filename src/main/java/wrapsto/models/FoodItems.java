package wrapsto.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
public class FoodItems {
    @Enumerated(EnumType.STRING)
    public category foodCategory;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "foodId")
    private int foodId;
    @Column(name = "foodName")
    private String foodName;
    @Column(name = "foodDescription")
    private String foodDescription;
    @Column(name = "amount")
    private int amount;
    @Length(max = 1000000000)
    @Column(name = "image")
    private String image;

    public category getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(category foodCategory) {
        this.foodCategory = foodCategory;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private enum category {
        PIZZA, BIRYANI, PASTA, WRAP
    }
}
