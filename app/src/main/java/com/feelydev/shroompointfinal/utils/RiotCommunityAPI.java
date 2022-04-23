package com.feelydev.shroompointfinal.utils;

import android.media.Image;

import com.feelydev.shroompointfinal.models.ChampionSimple;
import com.feelydev.shroompointfinal.models.ChampionVerbose;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RiotCommunityAPI {
    //Search for champion
    @GET("champions/{champId}.json")
    Call<ChampionVerbose> getChampion(
            @Path("champId") String champId
    );

    @GET("champion-summary.json")
    Call<List<ChampionSimple>> getAllChampions();

    @GET("champion-icons/{champId}.json")
    Call<Image> getThumbnail(
            @Path("champId") String champId
    );
}
