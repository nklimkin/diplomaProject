package model;

import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity{

    @Column(name = "label", nullable = false)
    @NotBlank
    private String label;

    @OneToMany(mappedBy = "restaurant")
    private List<Dish> menu;

    @Column(name = "rating")
    @NotBlank
    private double rating;

}
