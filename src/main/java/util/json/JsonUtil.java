package util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

public class JsonUtil {

    public static <T> String writeValue(T obj) {
        try {
            return MyObjectMapper.getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    private static <T> T readValue(String s, Class<T> clazz) {
        try {
            return MyObjectMapper.getMapper().readValue(s, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid read from JSON:\n'" + s + "'", e);
        }
    }

    public static <T> T readValueFromJson(ResultActions actions, Class<T> clazz) throws UnsupportedEncodingException {
        MvcResult mvcResult = actions.andReturn();
        return readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }
}
