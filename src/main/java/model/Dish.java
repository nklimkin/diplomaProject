package model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"label", "restaurant_id"}, name = "dish_restaurant")})
public class Dish extends BaseEntity{

    @Column(name = "label", nullable = false)
    @NotBlank
    private String label;

    @Column(name = "price", nullable = false)
    @NotBlank
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Dish(Integer id, @NotBlank String label, @NotBlank double price, Restaurant restaurant) {
        super(id);
        this.label = label;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, @NotBlank String label, @NotBlank double price) {
        super(id);
        this.label = label;
        this.price = price;
    }

    public Dish() {

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "label='" + label + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
