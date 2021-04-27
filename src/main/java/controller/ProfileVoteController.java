package controller;

import authorized.AuthorizedUser;
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
import java.security.Security;

@RestController
@RequestMapping(value = ProfileVoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController extends AbstractVoteController{

    public static final String URL = "/api/votes";

    private VoteService service;

    @Autowired
    public ProfileVoteController(VoteService service) {
        this.service = service;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo, @PathVariable int id,
                       @RequestParam("restaurant") int restaurantId,
                       @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        super.update(voteTo, id, authorizedUser.getId(), restaurantId);
    }

    @GetMapping
    public Vote get(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote todayVote = service.getTodayByUser(authorizedUser.getId());
        Assert.notNull(todayVote.getId(), "id of vote cant be null");
        return super.get(todayVote.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody VoteTo voteTo,
                                                   @RequestParam("restaurant") int restaurantId,
                                                   @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote v = super.save(voteTo, authorizedUser.getId(), restaurantId);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(v);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Vote todayVote = service.getTodayByUser(authorizedUser.getId());
        Assert.notNull(todayVote.getId(), "id of vote cant be null");
        super.delete(todayVote.getId());
    }


}
