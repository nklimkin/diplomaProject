package util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

    private static <T> List<T> readValues(String s, Class<T> clazz) {
        ObjectReader reader = MyObjectMapper.getMapper().readerFor(clazz);
        try {
            return reader.<T>readValues(s).readAll();
        } catch (IOException e) {
            throw new IllegalStateException("Invalid read from JSON:\n'" + s + "'", e);
        }
    }


    public static <T> T readValueFromJsonResultActions(ResultActions actions, Class<T> clazz) throws UnsupportedEncodingException {
        MvcResult mvcResult = actions.andReturn();
        return readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(result.getResponse().getContentAsString(), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValues(result.getResponse().getContentAsString(), clazz);
    }
}
