package util;

import org.springframework.test.web.servlet.ResultMatcher;
import util.json.JsonUtil;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcher {

    public static <T> ResultMatcher contentJson(T expected, String[] fieldsToIgnore, Class<T> clazz) {
        return mvcResult -> assertThat(JsonUtil.readFromJsonMvcResult(mvcResult, clazz))
                .usingRecursiveComparison().ignoringFields(fieldsToIgnore)
                .isEqualTo(expected);
    }

    public static  <T> ResultMatcher contentJson(Iterable<T> expected, String[] fieldsToIgnore, Class<T> clazz) {
        return mvcResult -> assertThat(JsonUtil.readListFromJsonMvcResult(mvcResult, clazz))
                .usingElementComparatorIgnoringFields(fieldsToIgnore)
                .isEqualTo(expected);
    }
}
