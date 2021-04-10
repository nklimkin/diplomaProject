package controller;

import model.Status;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.UserService;
import util.TestMatcher;
import util.json.JsonUtil;

import static org.assertj.core.api.Assertions.as;
import static org.junit.jupiter.api.Assertions.*;
import static testData.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerTest extends AbstractControllerTest{

    private final String REST_URL = ProfileController.REST_URL + "/";

    private final String[] fieldsToIgnore = new String[]{"registered"};

    @Autowired
    private UserService service;

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(getUpdated()))).andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1).getStatus()).isEqualTo(Status.DELETE);
    }
}