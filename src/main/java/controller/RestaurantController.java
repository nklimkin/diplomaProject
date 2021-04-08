package controller;

import model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.RestaurantService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    private final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    public static final String URL = "/api/restaurants";

    private RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> save(@RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Restaurant res = service.save(restaurant);
        URI uriOfNewResources = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(uriOfNewResources).body(res);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant restaurant) {
        log.info("get with restaurant {}", restaurant);
        service.update(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id={}", id);
        service.delete(id);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return service.get(id);
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
