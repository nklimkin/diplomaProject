package controller;

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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote) {
        Assert.notNull(vote.getId(), "vote id cant have be null");
        super.update(vote, vote.getId());
    }

    @GetMapping
    public Vote get() {
        Vote todayVote = service.getTodayByUser(SecurityUtil.authUserId());
        Assert.notNull(todayVote.getId(), "id of vote cant be null");
        return super.get(todayVote.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody Vote vote) {
        Vote v = super.save(vote);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(v);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        Vote todayVote = service.getTodayByUser(SecurityUtil.authUserId());
        Assert.notNull(todayVote.getId(), "id of vote cant be null");
        super.delete(todayVote.getId());
    }


}
