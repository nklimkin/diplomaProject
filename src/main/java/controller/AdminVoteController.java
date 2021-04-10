package controller;

import model.Restaurant;
import model.User;
import model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/admin/votes";

    private VoteService service;

    @Autowired
    public AdminVoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> save(@RequestBody Vote vote) {
        log.info("create vote {} ", vote);
        User userOfVote = vote.getUser();
        Restaurant restaurantOfVote = vote.getRestaurant();
        Assert.notNull(userOfVote.getId(), "user must not have id = null");
        Assert.notNull(restaurantOfVote.getId(), "restaurant must not have id = null");
        Vote v = service.create(vote, userOfVote.getId(), restaurantOfVote.getId());
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(v);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id) {
        log.info("update vote with id={}", id);
        User userOfVote = vote.getUser();
        Restaurant restaurantOfVote = vote.getRestaurant();
        Assert.notNull(userOfVote.getId(), "user must not have id = null");
        Assert.notNull(restaurantOfVote.getId(), "restaurant must not have id = null");
        service.update(vote, userOfVote.getId(), restaurantOfVote.getId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete vote with id={}", id);
        service.delete(id);
    }

    @GetMapping()
    public List<Vote> getAll() {
        log.info("get all votes");
        return service.getAll();
    }

    @GetMapping("/with-user-and-restaurant")
    public List<Vote> getWithUserAndRestaurant() {
        log.info("get votes with user and restaurant");
        return service.getVotesWithUserAndRestaurant();
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id={}", id);
        return service.get(id);
    }

    @GetMapping("/by")
    public List<Vote> getByRestaurant(@RequestParam int restaurantId) {
        log.info("get votes by restaurant with id={}", restaurantId);
        return service.getAllVoteByRestaurant(restaurantId);
    }

    @GetMapping("today/by")
    public List<Vote> getTodayByRestaurant(@RequestParam int restaurantId) {
        log.info("get today votes by restaurant with id={}", restaurantId);
        return service.getAllTodayVoteByRestaurant(restaurantId);
    }

    @GetMapping("/by")
    public List<Vote> getByRestaurantAndDate(@RequestParam int restaurantId, @RequestParam LocalDate date) {
        log.info("get vote by restaurant id = {} and date = {}", restaurantId, date);
        return service.getAllVoteByRestaurantAndLocalDate(restaurantId, date);
    }
}
