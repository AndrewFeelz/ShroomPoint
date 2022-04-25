package com.feelydev.shroompointfinal.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.adapters.ChampionVerboseViewModel;
import com.feelydev.shroompointfinal.databinding.ActivityChampionVerboseBinding;
import com.feelydev.shroompointfinal.models.ChampionVerbose;
import com.feelydev.shroompointfinal.models.Spell;
import com.feelydev.shroompointfinal.utils.Credentials;

import java.util.ArrayList;
import java.util.Collections;

public class ChampionVerboseActivity extends AppCompatActivity {

    private ActivityChampionVerboseBinding binding;

    //Model
    ChampionVerbose championActivityVerbose;

    //ViewModel
    ChampionVerboseViewModel championVerboseViewModel;

    //Widgets(6 imageViews and 19 textViews)
    private ImageView imgSplash, imgPassive, imgQ, imgW, imgE, imgR;
    private TextView txtName, txtTitle, txtBio, txtDifficulty, txtDamage, txtDurability, txtCC, txtMobility, txtUtility, txtPassiveName, txtPassiveDesc, txtQName, txtQDesc, txtWName, txtWDesc, txtEName, txtEDesc, txtRName, txtRDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemBars();
        setContentView(R.layout.activity_champion_verbose);
        binding = ActivityChampionVerboseBinding.inflate(getLayoutInflater());
        championVerboseViewModel = new ViewModelProvider(this).get(ChampionVerboseViewModel.class);
        String champID = getIntent().getStringExtra("champID");
        initializeWidgets();
        getChampionAPI(champID);
        ObserveChangesToChampion();

    }

    private void getChampionAPI(String champID){championVerboseViewModel.getChampionAPI(champID);}

    private void ObserveChangesToChampion(){
        championVerboseViewModel.getChampion().observe(this, new Observer<ChampionVerbose>() {
            @Override
            public void onChanged(ChampionVerbose championVerbose) {
                if(championVerbose != null){
                    championActivityVerbose = championVerbose;
                    SetChampion();
                    Log.v("Logger","ChampV Name from Activity: " + championVerbose.getName());
                }
            }
        });
    }

    private void SetChampion(){
        ArrayList<ImageView> spellImgList = new ArrayList<>();
        ArrayList<TextView> spellNameList = new ArrayList<>();
        ArrayList<TextView> spellDescList = new ArrayList<>();
        //counter variable
        int i = 0;
        Collections.addAll(spellImgList, imgQ, imgW, imgE, imgR);
        Collections.addAll(spellNameList, txtQName, txtWName, txtEName, txtRName);
        Collections.addAll(spellDescList, txtQDesc, txtWDesc, txtEDesc, txtRDesc );
        Log.v("Taggy", "My Champ is "+ championActivityVerbose.getId());
        String restOfUrl = championActivityVerbose.getId() + "/" + championActivityVerbose.getId() + "000.jpg";
        Glide.with(this)
                .load(Credentials.CHAMP_SPLASH_URL + restOfUrl)
                .into(imgSplash);
        txtName.setText(championActivityVerbose.getName());
        txtTitle.setText(championActivityVerbose.getTitle());
        txtBio.setText(championActivityVerbose.getShortBio());
        Log.v("Taggy", "My Champ is "+ championActivityVerbose.getName());
        txtDifficulty.setText(championActivityVerbose.getTacticalInfo().getDifficulty());
        txtDamage.setText(championActivityVerbose.getPlaystyleInfo().getDamage());
        txtDurability.setText(championActivityVerbose.getPlaystyleInfo().getDurability());
        txtCC.setText(championActivityVerbose.getPlaystyleInfo().getCrowdControl());
        txtMobility.setText(championActivityVerbose.getPlaystyleInfo().getMobility());
        txtUtility.setText(championActivityVerbose.getPlaystyleInfo().getUtility());

        Glide.with(this)
                .load(Credentials.CHAMP_SPELL_SQUARE_URL + championActivityVerbose.getPassive().getThumbnailPath())
                .into(imgPassive);
        txtPassiveName.setText(championActivityVerbose.getPassive().getName());
        txtPassiveDesc.setText(championActivityVerbose.getPassive().getDesc());

        for (Spell spell: championActivityVerbose.getSpells()) {
            ImageView img =  spellImgList.get(i);
            Glide.with(this)
                    .load(Credentials.CHAMP_SPELL_SQUARE_URL + spell.getThumbnailPath())
                    .into(img);

            TextView name = spellNameList.get(i);
            name.setText(spell.getName());

            TextView desc = spellDescList.get(i);
            desc.setText(spell.getDesc());

            i++;
        }
    }


    private void initializeWidgets() {
        imgSplash = findViewById(R.id.imgChampionSplash);
        imgPassive = findViewById(R.id.imgPassive);
        imgQ = findViewById(R.id.imgQ);
        imgW = findViewById(R.id.imgW);
        imgE = findViewById(R.id.imgE);
        imgR = findViewById(R.id.imgR);

        txtName = findViewById(R.id.txtChampName);
        txtTitle = findViewById(R.id.txtChampTitle);
        txtBio = findViewById(R.id.txtBio);
        txtDifficulty = findViewById(R.id.txtDifficultyNum);
        txtDamage = findViewById(R.id.txtDamageNum);
        txtDurability = findViewById(R.id.txtDurabilityNum);
        txtCC = findViewById(R.id.txtCCNum);
        txtMobility = findViewById(R.id.txtMobilityNum);
        txtUtility = findViewById(R.id.txtUtilityNum);
        txtPassiveName = findViewById(R.id.txtPassiveName);
        txtPassiveDesc = findViewById(R.id.txtPassiveDescription);
        txtQName = findViewById(R.id.txtQName);
        txtQDesc = findViewById(R.id.txtQDescription);
        txtWName = findViewById(R.id.txtWName);
        txtWDesc = findViewById(R.id.txtWDescription);
        txtEName = findViewById(R.id.txtEName);
        txtEDesc = findViewById(R.id.txtEDescription);
        txtRName = findViewById(R.id.txtRName);
        txtRDesc = findViewById(R.id.txtRDescription);
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