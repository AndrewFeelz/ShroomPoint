package com.feelydev.shroompointfinal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChampionSimple implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("alias")
    private String alias;

    @SerializedName("roles")
    private ArrayList<String> roles;

    public ChampionSimple() {
    }

    public ChampionSimple(int id, String name, String alias, ArrayList<String> roles) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.roles = roles;
    }

    protected ChampionSimple(Parcel in) {
        id = in.readInt();
        name = in.readString();
        alias = in.readString();
        roles = in.createStringArrayList();
    }

    public static final Creator<ChampionSimple> CREATOR = new Creator<ChampionSimple>() {
        @Override
        public ChampionSimple createFromParcel(Parcel in) {
            return new ChampionSimple(in);
        }

        @Override
        public ChampionSimple[] newArray(int size) {
            return new ChampionSimple[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getRoles() {
        String roleReturn ="";
        for (String role: roles) {
            roleReturn+= role + " ";
        }
        return roleReturn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(alias);
        parcel.writeStringList(roles);
    }
}
