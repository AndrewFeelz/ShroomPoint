package com.feelydev.shroompointfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.feelydev.shroompointfinal.adapters.ChampionVerboseViewModel;
import com.feelydev.shroompointfinal.models.ChampionVerbose;

public class ChampionVerboseActivity extends AppCompatActivity {

    //Model
    ChampionVerbose championVerbose;

    //ViewModel
    ChampionVerboseViewModel championVerboseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBars();
        setContentView(R.layout.activity_champion_verbose);
        championVerboseViewModel = new ViewModelProvider(this).get(ChampionVerboseViewModel.class);
        getChampionAPI("1");
        ObserveChangesToChampion();






    }

    private void getChampionAPI(String champID){championVerboseViewModel.getChampionAPI(champID);}

    private void ObserveChangesToChampion(){
        championVerboseViewModel.getChampion().observe(this, new Observer<ChampionVerbose>() {
            @Override
            public void onChanged(ChampionVerbose championVerbose) {
                if(championVerbose != null){
                    Log.v("Logger","ChampV Name from Activity: " + championVerbose.getName());
                }
            }
        });
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}