package com.demo.cooking.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shady Shalaby on 08/06/2017.
 */

public class MealStep {

    @SerializedName("stepnumber")
    private int stepNumber;
    @SerializedName("secondtimer")
    private int secondTimer;
    @SerializedName("description")
    private String description;

    public MealStep() {
        this.stepNumber = 0;
        this.secondTimer = 0;
        this.description = "";
    }

    public MealStep(int stepNumber, int secondTimer, String description) {
        this.stepNumber = stepNumber;
        this.secondTimer = secondTimer;
        this.description = description;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getSecondTimer() {
        return secondTimer;
    }

    public void setSecondTimer(int secondTimer) {
        this.secondTimer = secondTimer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
