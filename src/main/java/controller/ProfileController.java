package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import util.SecurityUtil;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/api/profiles";

    private UserService service;

    @Autowired
    public ProfileController(UserService service) {
        this.service = service;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        log.info("update user with id={}", SecurityUtil.authUserId());
        service.update(user, SecurityUtil.authUserId());
    }

    @GetMapping
    public User get() {
        log.info("get user with id={}", SecurityUtil.authUserId());
        return service.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("delete user with id={}", SecurityUtil.authUserId());
        service.delete(SecurityUtil.authUserId());
    }
}
