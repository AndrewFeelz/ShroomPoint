package com.feelydev.shroompointfinal.models;

import com.google.gson.annotations.SerializedName;

public class Playstyle {

    @SerializedName("damage")
    private int damage;

    @SerializedName("durability")
    private int durability;

    @SerializedName("crowdControl")
    private int crowdControl;

    @SerializedName("mobility")
    private int mobility;

    @SerializedName("utility")
    private int utility;

    public Playstyle() {
    }

    public Playstyle(int damage, int durability, int crowdControl, int mobility, int utility) {
        this.damage = damage;
        this.durability = durability;
        this.crowdControl = crowdControl;
        this.mobility = mobility;
        this.utility = utility;
    }

    public int getDamage() {
        return damage;
    }

    public int getDurability() {
        return durability;
    }

    public int getCrowdControl() {
        return crowdControl;
    }

    public int getMobility() {
        return mobility;
    }

    public int getUtility() {
        return utility;
    }
}
