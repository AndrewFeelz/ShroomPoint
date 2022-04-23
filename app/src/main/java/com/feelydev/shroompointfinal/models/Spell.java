package com.feelydev.shroompointfinal.models;

import com.google.gson.annotations.SerializedName;

public class Spell {

    @SerializedName("spellKey")
    private String spellKey;

    @SerializedName("name")
    private String name;

    @SerializedName("abilityIconPath")
    private String thumbnailPath;

    @SerializedName("description")
    private String desc;

    @SerializedName("range")
    private String[] range;

    @SerializedName("costCoefficients")
    private String[] cost;

    @SerializedName("cooldownCoefficients")
    private String[] cooldown;

    public Spell() {
    }

    public Spell(String spellKey, String name, String thumbnailPath, String desc, String[] range, String[] cost, String[] cooldown) {
        this.spellKey = spellKey;
        this.name = name;
        this.thumbnailPath = thumbnailPath;
        this.desc = desc;
        this.range = range;
        this.cost = cost;
        this.cooldown = cooldown;
    }

    public String getSpellKey() {
        return spellKey;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailPath() {
        thumbnailPath = thumbnailPath.substring(39);
        thumbnailPath = thumbnailPath.toLowerCase();
        return thumbnailPath;
    }

    public String getDesc() {
        return desc;
    }

    public String[] getRange() {
        return range;
    }

    public String[] getCost() {
        return cost;
    }

    public String[] getCooldown() {
        return cooldown;
    }
}
