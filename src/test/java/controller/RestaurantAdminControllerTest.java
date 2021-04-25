package controller;

import model.Restaurant;
import model.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.RestaurantService;
import util.TestMatcher;
import util.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testData.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantAdminControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    private static final String REST_URL = RestaurantAdminController.URL + "/";

    private static final String[] fieldsToIgnore = new String[]{"menu", "date"};

    @Test
    public void save() throws Exception {
        Restaurant restaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(restaurant)))
                    .andDo(print());

        Restaurant created = JsonUtil.readValueFromJsonResultActions(action, Restaurant.class);
        int id = created.getId();
        restaurant.setId(id);

        assertThat(created).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(restaurant);

        assertThat(restaurant).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(service.get(id));
    }

    @Test
    public void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated))).andExpect(status().isNoContent()).andDo(print());

        assertThat(service.get(updated.getId())).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    public void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_1)).andExpect(status().isNoContent())
        .andDo(print());
        assertThat(service.get(RESTAURANT_1).getStatus()).isEqualTo(Status.DELETE);
    }

    @Test
    public void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TestMatcher.contentJson(restaurant1, fieldsToIgnore, Restaurant.class));
    }

    @Test
    public void getAll() throws Exception {
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