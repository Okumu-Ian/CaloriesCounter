package models;

import java.io.Serializable;

public class UserModel implements Serializable {
    private String name,age,weight,height;

    public UserModel(String name, String age, String weight, String height) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public UserModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
