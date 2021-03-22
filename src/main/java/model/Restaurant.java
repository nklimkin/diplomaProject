package model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{

    @Column(name = "label", nullable = false)
    @NotBlank
    private String label;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Dish> menu;

    @Column(name = "rating")
    @NotNull
    private double rating;

    @Column(name = "number_of_voters")
    @NotNull
    private int countOfVoters;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    public Restaurant(Integer id, @NotBlank String label, List<Dish> menu, @NotBlank double rating, int countOfVoters, LocalDate date, Status status) {
        super(id);
        this.label = label;
        this.menu = menu;
        this.rating = rating;
        this.countOfVoters = countOfVoters;
        this.status = status;
        this.date = date;
    }

    public Restaurant() {
    }

    public Restaurant(Restaurant r){
        this(r.getId(), r.getLabel(), r.getMenu(), r.rating, r.countOfVoters, r.date, r.status);
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "label='" + label + '\'' +
                ", rating=" + rating +
                ", countOfVoters=" + countOfVoters +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
