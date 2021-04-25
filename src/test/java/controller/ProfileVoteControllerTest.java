package controller;

import model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.VoteService;
import testData.UserTestData;
import util.Exception.NotFoundException;
import util.TestMatcher;
import util.JsonUtil;
import util.SecurityUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testData.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest{

    private final String REST_URL = ProfileVoteController.URL + "/";

    private final String[] fieldToIgnore = new String[]{"localTime", "restaurant", "user"};

    @Autowired
    private VoteService service;

    @Test
    void update() throws Exception {
        SecurityUtil.setAuthUserId(UserTestData.ADMIN1);
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedDontChangeRestaurant())))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(VOTE_1)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(updatedVoteWithoutChangingRestaurant);

        SecurityUtil.setAuthUserId(UserTestData.USER1);

    }

    @Test
    void get() throws Exception {
        SecurityUtil.setAuthUserId(UserTestData.ADMIN1);
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestMatcher.contentJson(vote1, fieldToIgnore, Vote.class));
        SecurityUtil.setAuthUserId(UserTestData.USER1);
    }

    @Test
    void createWithLocation() throws Exception {
        Vote vote = getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isCreated())
                .andDo(print());

        Vote created = JsonUtil.readValueFromJsonResultActions(actions, Vote.class);
        int id = created.getId();
        vote.setId(id);

        assertThat(created).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(vote);

        assertThat(vote).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(service.get(VOTE_NEW));
    }

    @Test
    void delete() throws Exception {
        SecurityUtil.setAuthUserId(UserTestData.ADMIN1);
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThrows(NotFoundException.class, () -> service.get(VOTE_1));
        SecurityUtil.setAuthUserId(UserTestData.USER1);
    }
}