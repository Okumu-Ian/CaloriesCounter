package models;

import java.io.Serializable;

public class TargetModel implements Serializable {
    private String weight,height;

    public TargetModel() {
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
