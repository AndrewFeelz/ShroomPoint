package com.feelydev.shroompointfinal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChampionVerbose implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("alias")
    @Expose
    private String alias;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("shortBio")
    @Expose
    private String shortBio;

    @SerializedName("tacticalInfo")
    @Expose
    private Tactility tacticalInfo;

    @SerializedName("playstyleInfo")
    @Expose
    private Playstyle playstyleInfo;

    @SerializedName("roles")
    @Expose
    private List<String> roles;

    @SerializedName("passive")
    @Expose
    private Passive passive;

    @SerializedName("spells")
    @Expose
    private List<Spell> spells;

    public ChampionVerbose() {
    }

    public ChampionVerbose(int id, String name, String alias, String title, String shortBio, Tactility tacticalInfo, Playstyle playstyleInfo, List<String> roles, Passive passive, List<Spell> spells) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.title = title;
        this.shortBio = shortBio;
        this.tacticalInfo = tacticalInfo;
        this.playstyleInfo = playstyleInfo;
        this.roles = roles;
        this.passive = passive;
        this.spells = spells;
    }

    protected ChampionVerbose(Parcel in) {
        id = in.readInt();
        name = in.readString();
        alias = in.readString();
        title = in.readString();
        shortBio = in.readString();
        roles = in.createStringArrayList();
    }

    public static final Creator<ChampionVerbose> CREATOR = new Creator<ChampionVerbose>() {
        @Override
        public ChampionVerbose createFromParcel(Parcel in) {
            return new ChampionVerbose(in);
        }

        @Override
        public ChampionVerbose[] newArray(int size) {
            return new ChampionVerbose[size];
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

    public String getTitle() {
        return title;
    }

    public String getShortBio() {
        return shortBio;
    }

    public Tactility getTacticalInfo() {
        return tacticalInfo;
    }

    public Playstyle getPlaystyleInfo() {
        return playstyleInfo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Passive getPassive() {
        return passive;
    }

    public List<Spell> getSpells() {
        return spells;
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
        parcel.writeString(title);
        parcel.writeString(shortBio);
        parcel.writeStringList(roles);
    }
}
