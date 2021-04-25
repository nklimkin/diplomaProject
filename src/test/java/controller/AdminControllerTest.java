package controller;

import model.Status;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.UserService;
import util.TestMatcher;
import util.JsonUtil;
import util.SecurityUtil;
import util.TestSecurityUtil;

import static testData.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

class AdminControllerTest extends AbstractControllerTest{

    private final String REST_URL = AdminController.URL + "/";

    private final String[] fieldsToIgnore = new String[]{"registered"};

    @Autowired
    private UserService service;

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + USER1)
                .with(TestSecurityUtil.userHttpBasic(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER1)
                .with(TestSecurityUtil.userHttpBasic(user1)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1).getStatus()).isEqualTo(Status.DELETE);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void getUserByEmail() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by?email=" + user1.getEmail()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(users, fieldsToIgnore, User.class));
    }
}