package to;

import javax.validation.constraints.NotNull;

public class BaseTo {

    private Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
