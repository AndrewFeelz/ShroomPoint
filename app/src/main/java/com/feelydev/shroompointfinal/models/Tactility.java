package com.feelydev.shroompointfinal.models;

import com.google.gson.annotations.SerializedName;

public class Tactility {

    @SerializedName("style")
    private String style;

    @SerializedName("difficulty")
    private String difficulty;

    @SerializedName("damageType")
    private String damageType;

    public Tactility() {
    }

    public Tactility(String style, String difficulty, String damageType) {
        this.style = style;
        this.difficulty = difficulty;
        this.damageType = damageType;
    }

    public String getStyle() {
        return style;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDamageType() {
        return damageType;
    }
}
