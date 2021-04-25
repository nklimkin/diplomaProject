package controller;

import model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantProfileController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProfileController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private RestaurantService service;
    public static final String URL = "/api/restaurants";

    @Autowired
    public RestaurantProfileController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    @GetMapping("/best")
    public Restaurant getBestOne() {
        log.info("get best restaurant");
        return service.getTopRestaurant();
    }

    @GetMapping("/with-dishes/{id}")
    public Restaurant getWithMenu(@PathVariable int id) {
        log.info("get restaurant with id={} with id", id);
        return service.getWithMenu(id);
    }






}
