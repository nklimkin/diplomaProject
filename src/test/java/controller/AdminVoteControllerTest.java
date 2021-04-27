package controller;

import model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.VoteService;
import testData.RestaurantTestData;
import testData.UserTestData;
import to.VoteTo;
import util.*;
import util.Exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static testData.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest{

    private static final String REST_URL = AdminVoteController.URL + "/";

    private final String[] fieldToIgnore = new String[]{"localTime", "restaurant", "user"};

    @Autowired
    private VoteService service;

    @Test
    void save() throws Exception {
        VoteTo voteTo = getNewTo();
        ResultActions actions = perform(MockMvcRequestBuilders.post(AdminVoteController.URL + "?restaurant=" + RestaurantTestData.RESTAURANT_1)
            .with(TestSecurityUtil.userHttpBasic(UserTestData.user1))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtil.writeValue(voteTo)))
                .andExpect(status().isCreated())
                .andDo(print());

        Vote created = JsonUtil.readValueFromJsonResultActions(actions, Vote.class);
        int id = created.getId();
        Vote vote = VoteUtil.createVoteFromVoteTo(voteTo);
        vote.setUser(UserTestData.user1);
        vote.setRestaurant(RestaurantTestData.restaurant1);
        vote.setId(id);

        assertThat(created).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(vote);

        assertThat(vote).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(service.get(VOTE_NEW));
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + VOTE_1 + "?restaurant=" + RestaurantTestData.RESTAURANT_1)
                .with(TestSecurityUtil.userHttpBasic(UserTestData.admin1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedTo())))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThat(service.get(VOTE_1)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(updatedVoteWithoutChangingRestaurant);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + VOTE_1)
                .with(TestSecurityUtil.userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertThrows(NotFoundException.class, () -> service.get(VOTE_1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestSecurityUtil.userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestMatcher.contentJson(votes, fieldToIgnore, Vote.class));

    }

    @Test
    void getWithUserAndRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-user-and-restaurant")
                .with(TestSecurityUtil.userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestMatcher.contentJson(votes, new String[]{"localTime"}, Vote.class));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + VOTE_1)
                .with(TestSecurityUtil.userHttpBasic(UserTestData.admin1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestMatcher.contentJson(vote1, fieldToIgnore, Vote.class));
    }
}