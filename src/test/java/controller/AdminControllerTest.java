package controller;

import model.Status;
import model.User;
import model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.UserService;
import testData.UserTestData;
import to.UserTo;
import util.*;

import static testData.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static testData.VoteTestData.VOTE_NEW;

class AdminControllerTest extends AbstractControllerTest{

    private final String REST_URL = AdminController.URL + "/";

    private final String[] fieldsToIgnore = new String[]{"registered", "password"};

    @Autowired
    private UserService service;

    @Test
    void save() throws Exception {

        User user = getNew();
        UserTo userTo = UserUtil.convertUserToUserTo(user);
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(TestSecurityUtil.userHttpBasic(admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userTo)))
                .andExpect(status().isCreated())
                .andDo(print());

        User created = JsonUtil.readValueFromJsonResultActions(actions, User.class);
        int id = created.getId();
        user.setId(id);

        assertThat(created).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(user);

        assertThat(user).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(service.get(NEW));
    }

    @Test
    void update() throws Exception {
        UserTo updateTo = UserUtil.convertUserToUserTo(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + USER1)
                .with(TestSecurityUtil.userHttpBasic(admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updateTo)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER1)
                .with(TestSecurityUtil.userHttpBasic(admin1)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1).getStatus()).isEqualTo(Status.DELETE);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER1)
                .with(TestSecurityUtil.userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void getUserByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by?email=" + user1.getEmail())
                .with(TestSecurityUtil.userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestSecurityUtil.userHttpBasic(admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(users, fieldsToIgnore, User.class));
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
            .with(TestSecurityUtil.userHttpBasic(user1)))
            .andExpect(status().isForbidden());
    }
}