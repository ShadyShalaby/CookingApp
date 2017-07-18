package com.demo.cooking.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Shady Shalaby on 08/06/2017.
 */

public class MealDetails {

    @SerializedName("ingredients")
    private ArrayList<String> ingredients;
    @SerializedName("steps")
    private ArrayList<MealStep> steps;

    public MealDetails() {
        ingredients = new ArrayList<String>();
        steps = new ArrayList<MealStep>();
    }

    public MealDetails(ArrayList<String> ingredients, ArrayList<MealStep> steps) {
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<MealStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<MealStep> steps) {
        this.steps = steps;
    }

}
