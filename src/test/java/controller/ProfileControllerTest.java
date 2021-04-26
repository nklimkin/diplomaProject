package controller;

import model.Status;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.UserService;
import to.UserTo;
import util.TestMatcher;
import util.JsonUtil;
import util.TestSecurityUtil;
import util.UserUtil;

import static testData.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerTest extends AbstractControllerTest{

    private final String REST_URL = ProfileController.REST_URL + "/";

    private final String[] fieldsToIgnore = new String[]{"registered", "password"};

    @Autowired
    private UserService service;

    @Test
    void resister() throws Exception {

        User user = getNew();
        UserTo userTo = UserUtil.convertUserToUserTo(user);
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "register")
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
        UserTo updatedTo = UserUtil.convertUserToUserTo(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL)
            .with(TestSecurityUtil.userHttpBasic(user1))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(updatedTo))).andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestSecurityUtil.userHttpBasic(user1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(user1, fieldsToIgnore, User.class));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(TestSecurityUtil.userHttpBasic(user1)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(USER1).getStatus()).isEqualTo(Status.DELETE);
    }
}