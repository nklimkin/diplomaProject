package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import service.UserService;

public class AbstractUserController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public void update(User user, int userId) {
        log.info("update user {} with id={}", user, userId);
        service.update(user, userId);
    }

    public void delete(int id) {
        log.info("delete user with id={}", id);
        service.delete(id);
    }

    public User get(int id) {
        log.info("get user with id={}", id);
        return service.get(id);
    }


}
