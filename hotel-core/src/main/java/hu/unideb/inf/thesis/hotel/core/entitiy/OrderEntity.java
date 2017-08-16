package hu.unideb.inf.thesis.hotel.core.entitiy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class OrderEntity extends BaseEntity {

    @Basic
    @Column(nullable = false)
    private Date time;

    @Basic
    @Column(nullable = false)
    private int totalPrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<FoodEntity> foods;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<DrinkEntity> drinks;

    public OrderEntity() {
    }

    public OrderEntity(Date time, int totalPrice) {
        this.time = time;
        this.totalPrice = totalPrice;
        this.foods = new ArrayList<FoodEntity>();
        this.drinks = new ArrayList<DrinkEntity>();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<FoodEntity> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodEntity> foods) {
        this.foods = foods;
    }

    public List<DrinkEntity> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<DrinkEntity> drinks) {
        this.drinks = drinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderEntity that = (OrderEntity) o;

        if (totalPrice != that.totalPrice) return false;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + totalPrice;
        return result;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "time=" + time +
                ", totalPrice=" + totalPrice +
                '}';
    }
}