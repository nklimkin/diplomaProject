package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = AdminController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/admin/users";

    private UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        log.info("update user {} with id={}", user, id);
        service.update(user, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete user with id={}", id);
        service.delete(id);
    }

    @GetMapping(value = "/{id}")
    public User get(@PathVariable int id) {
        log.info("get user with id={}", id);
        return service.get(id);
    }

    @GetMapping(value = "/by")
    public User getUserByEmail(@RequestParam String email) {
        log.info("get user by email={}", email);
        return service.getUserByEmail(email);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("get all users");
        return service.getAll();
    }
}
