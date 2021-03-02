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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
