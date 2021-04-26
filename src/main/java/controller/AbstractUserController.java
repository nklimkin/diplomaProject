package controller;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import service.UserService;
import to.UserTo;
import util.UserUtil;

public class AbstractUserController {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public User create(User user) {
        log.info("create user {}", user);
        return service.save(user);
    }

    public User create(UserTo userTo) {
        log.info("create {}", userTo);
        return service.save(UserUtil.createNewFromTO(userTo));
    }

    public void update(User user, int userId) {
        log.info("update user {} with id={}", user, userId);
        service.update(user, userId);
    }

    public void update(UserTo userTo, int id) {
        log.info("update  user {} with id={}", userTo, id);
        service.update(userTo, id);
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
