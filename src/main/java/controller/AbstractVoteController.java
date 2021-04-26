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
import to.VoteTo;
import util.SecurityUtil;
import util.ValidationUtil;

import java.net.URI;

public abstract class AbstractVoteController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private VoteService service;

    public void update(VoteTo voteTo, int voteToId, int userId, int restaurantId) {
        log.info("update vote {} with id={}", voteTo, voteToId);
        ValidationUtil.assureIdConsistent(voteTo, voteToId);
        service.update(voteTo, userId, restaurantId);
    }

    public void delete(int id) {
        log.info("delete vote with id={}", id);
        service.delete(id);
    }

    public Vote save(VoteTo voteTo, int userId, int restaurantId) {
        log.info("create vote {} ", voteTo);
        return service.create(voteTo, userId, restaurantId);
    }

    public Vote get(int id) {
        log.info("get vote with id={}", id);
        return service.get(id);
    }

}
