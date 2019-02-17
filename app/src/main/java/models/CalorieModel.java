package models;

import java.io.Serializable;

public class CalorieModel implements Serializable {
    private String calorieId, foodName, calorieAmount,Date;

    public CalorieModel(String calorieId, String foodName, String calorieAmount, String date) {
        this.calorieId = calorieId;
        this.foodName = foodName;
        this.calorieAmount = calorieAmount;
        Date = date;
    }

    public CalorieModel() {
    }

    public String getCalorieId() {
        return calorieId;
    }

    public void setCalorieId(String calorieId) {
        this.calorieId = calorieId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCalorieAmount() {
        return calorieAmount;
    }

    public void setCalorieAmount(String calorieAmount) {
        this.calorieAmount = calorieAmount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
