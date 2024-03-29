package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.UserService;
import to.UserTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController extends AbstractUserController{

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String URL = "/api/admin/users";

    private UserService service;

    @Autowired
    public AdminController(UserService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createWithLocation(@RequestBody UserTo userTo) {
        User creatingUser = super.create(userTo);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL + "/{id}")
                .buildAndExpand(creatingUser.getId()).toUri();
        return ResponseEntity.created(uri).body(creatingUser);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @PathVariable int id) {
        super.update(userTo, id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(value = "/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
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
