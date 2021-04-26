package controller;

import authorized.AuthorizedUser;
import model.Restaurant;
import model.User;
import model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.VoteService;
import to.VoteTo;
import util.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController{

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/admin/votes";

    private VoteService service;

    @Autowired
    public AdminVoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> saveWithLocation(@RequestBody VoteTo voteTo,
                                                 @RequestParam("restaurant") int restaurantId,
                                                 @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote v = super.save(voteTo, authorizedUser.getId(), restaurantId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(v);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo, @PathVariable int id,
                       @RequestParam("restaurant") int restaurantId,
                       @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        super.update(voteTo, id, authorizedUser.getId(), restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
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
        return super.get(id);
    }
}
