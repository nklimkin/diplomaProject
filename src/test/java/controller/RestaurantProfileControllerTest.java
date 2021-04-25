package controller;

import model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import util.TestMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static testData.RestaurantTestData.*;
import static testData.RestaurantTestData.restaurant1;

class RestaurantProfileControllerTest extends AbstractControllerTest{

    private static String REST_URL = RestaurantProfileController.URL + "/";

    private static final String[] fieldsToIgnore = new String[]{"menu", "date"};

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(restaurants, fieldsToIgnore, Restaurant.class));

    }

    @Test
    public void getBestOne() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "best"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(restaurant3, fieldsToIgnore, Restaurant.class));
    }

    @Test
    public void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-dishes/" + RESTAURANT_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(TestMatcher.contentJson(restaurant1, new String[]{"date"}, Restaurant.class));

    }
}