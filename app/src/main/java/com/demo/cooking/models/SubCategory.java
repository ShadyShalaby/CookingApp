package com.demo.cooking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shady Shalaby on 09/04/2017.
 */

public class SubCategory implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("subname")
    private String name;
    @SerializedName("image")
    private String image;

    public SubCategory() {
        id = 0;
        name = "";
        image = "https:";
    }

    public SubCategory(int id, String name) {
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
}
