package controller;

import model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.VoteService;
import util.SecurityUtil;

import java.net.URI;
import java.security.Security;

@RestController
@RequestMapping(value = ProfileVoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileVoteController extends AbstractVoteController{

    private Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/votes";

    private VoteService service;

    @Autowired
    public ProfileVoteController(VoteService service) {
        this.service = service;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update() {
        Vote updated = service.getTodayByUser(SecurityUtil.authUserId());
        super.update(updated, updated.getId());
    }

    @GetMapping
    public Vote get() {
        return super.get(SecurityUtil.authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(Vote vote) {
        Vote v = super.save(vote);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(v.getId()).toUri();
        return ResponseEntity.created(uri).body(v);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(SecurityUtil.authUserId());
    }


}
