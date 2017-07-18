package com.demo.cooking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shady Shalaby on 09/04/2017.
 */

public class Category implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("img")
    private String image;

    public Category() {
        id = 0;
        name = "";
        image = "http:";
    }

    public Category(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ID " + id
                + " name " + name
                + " image " + image;
    }
}
