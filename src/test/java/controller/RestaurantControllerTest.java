package controller;

import model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import service.RestaurantService;
import util.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testData.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService service;

    private static final String REST_URL = RestaurantController.URL + "/";

    private static final String[] fieldsToIgnore = new String[]{"menu", "date"};

    @Test
    public void save() throws Exception {
        Restaurant restaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(restaurant)))
                    .andDo(print());

        Restaurant created = JsonUtil.readValueFromJson(action, Restaurant.class);
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
        perform(MockMvcRequestBuilders.put(REST_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.writeValue(updated))).andExpect(status().isNoContent()).andDo(print());

        assertThat(service.get(updated.getId())).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updated);
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void getBestOne() {
    }

    @Test
    public void getWithMenu() {
    }
}