package model;

import org.springframework.stereotype.Component;

import java.util.List;

public class Restaurant extends BaseEntity{

    private String label;

    private List<Dish> menu;

    private double rating;

}
