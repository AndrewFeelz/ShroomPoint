package com.feelydev.shroompointfinal.models;

import com.google.gson.annotations.SerializedName;

public class Playstyle {

    @SerializedName("damage")
    private String damage;

    @SerializedName("durability")
    private String durability;

    @SerializedName("crowdControl")
    private String crowdControl;

    @SerializedName("mobility")
    private String mobility;

    @SerializedName("utility")
    private String utility;

    public Playstyle() {
    }

    public Playstyle(String damage, String durability, String crowdControl, String mobility, String utility) {
        this.damage = damage;
        this.durability = durability;
        this.crowdControl = crowdControl;
        this.mobility = mobility;
        this.utility = utility;
    }

    public String getDamage() {
        return damage;
    }

    public String getDurability() {
        return durability;
    }

    public String getCrowdControl() {
        return crowdControl;
    }

    public String getMobility() {
        return mobility;
    }

    public String getUtility() {
        return utility;
    }
}
