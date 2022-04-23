package com.feelydev.shroompointfinal.adapters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feelydev.shroompointfinal.models.ChampionVerbose;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.feelydev.shroompointfinal.utils.RiotCommunityAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChampionVerboseViewModel extends ViewModel {

    private MutableLiveData<ChampionVerbose> championVerboseMutableLiveData = new MutableLiveData<>();
    private ChampionVerbose championVerbose;

    public ChampionVerboseViewModel() { }

    public void getChampionAPI(String champID){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RiotCommunityAPI service = retrofit.create(RiotCommunityAPI.class);

        Call<ChampionVerbose> championVerboseCall = service.getChampion(champID);
        championVerboseCall.enqueue(new Callback<ChampionVerbose>() {
            @Override
            public void onResponse(Call<ChampionVerbose> call, Response<ChampionVerbose> response) {
                if (response.code() == 200){
                    championVerbose = response.body();
                    championVerboseMutableLiveData.postValue(championVerbose);
                    Log.v("Logger","ChampV Name: " + championVerbose.getName());
                } else {
                    Log.v("Logger", "ChampionV response code: " + response.code());
                    championVerboseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ChampionVerbose> call, Throwable t) {
                Log.v("Logger", "Really done something wrong with the ChampionV call");
                championVerboseMutableLiveData.postValue(null);
            }
        });
    }

    public LiveData<ChampionVerbose> getChampion(){return championVerboseMutableLiveData;}
}
