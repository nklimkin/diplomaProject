package to;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class VoteTo extends BaseTo implements Serializable {

    @NotNull
    @Min(0)
    @Max(10)
    private int grade;

    // Ресторан за который проголосовали до обновления голоса
    private Integer restaurantId;

    public VoteTo() {

    }

    public VoteTo(Integer id, int grade, Integer restaurantId) {
        super(id);
        this.grade = grade;
        this.restaurantId = restaurantId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
