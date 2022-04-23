package com.feelydev.shroompointfinal.adapters;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.feelydev.shroompointfinal.models.ChampionSimple;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.feelydev.shroompointfinal.utils.RiotCommunityAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChampionsViewModel extends ViewModel {

    private MutableLiveData<List<ChampionSimple>> championList = new MutableLiveData<>();
    private List<ChampionSimple> championListing;

    public ChampionsViewModel() { }

    public void getChampionListAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RiotCommunityAPI service = retrofit.create(RiotCommunityAPI.class);

        Call<List<ChampionSimple>> champListCall = service.getAllChampions();
        champListCall.enqueue(new Callback<List<ChampionSimple>>() {
            @Override
            public void onResponse(Call<List<ChampionSimple>> call, Response<List<ChampionSimple>> response) {
                int i = 1;
                if (response.code() == 200){
                    championListing = response.body();
                    championListing.remove(0);
                    championList.postValue(championListing);
                    for (ChampionSimple simple: championListing) {
                        Log.v("Logger", simple.getName() + " count: " + i);
                        i++;

                    }
                } else {
                    Log.v("Logger", "Response code: " + response.code());
                    championList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<ChampionSimple>> call, Throwable t) {
                Log.v("Logger", "Really done something wrong");
                championList.postValue(null);
            }
        });
    }

    public LiveData<List<ChampionSimple>> getChampionList(){ return championList;}

}