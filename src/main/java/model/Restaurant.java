package model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{

    @Column(name = "label", nullable = false)
    @NotBlank
    private String label;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Dish> menu;

    @Column(name = "rating")
    @NotNull
    private double rating;

    @Column(name = "number_of_voters")
    @NotNull
    private int countOfVoters;

    public Restaurant(Integer id, @NotBlank String label, List<Dish> menu, @NotBlank double rating, int countOfVoters) {
        super(id);
        this.label = label;
        this.menu = menu;
        this.rating = rating;
        this.countOfVoters = countOfVoters;
    }

    public Restaurant() {
    }

    public Restaurant(Restaurant r){
        this(r.getId(), r.getLabel(), r.getMenu(), r.rating, r.countOfVoters);
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    public void addDish(Dish dish){
        this.menu.add(dish);
        dish.setRestaurant(this);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCountOfVoters() {
        return countOfVoters;
    }

    public void setCountOfVoters(int countOfVoters) {
        this.countOfVoters = countOfVoters;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "label='" + label + '\'' +
                ", menu=" + menu +
                ", rating=" + rating +
                '}';
    }
}
