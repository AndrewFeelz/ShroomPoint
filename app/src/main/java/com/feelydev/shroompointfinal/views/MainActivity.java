package com.feelydev.shroompointfinal.views;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.feelydev.shroompointfinal.views.LoginActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.feelydev.shroompointfinal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SharedPreferences preferences;

    //User
    private String username;
    private String email;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get User
        preferences = getSharedPreferences(Credentials.PREF_FILE_NAME, MODE_PRIVATE);
        username = preferences.getString("userName", "");
        email = preferences.getString("email", "");


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.logout, R.id.navigation_champions, R.id.navigation_profile)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);

        BottomNavigationItemView logoutBtn = findViewById(R.id.logout);
        if (username.equals("") && email.equals("")){
            logoutBtn.setTitle("Login");
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
        } else{
            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
        }
    }

    private void logout() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.teemo_laugh);
        mp.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setTitle("Wait!")
                .setPositiveButton("For Sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AuthUI.getInstance()
                                .signOut(getApplicationContext());
                        preferences.edit().clear().apply();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        mp.stop();
                    }
                })
                .setNegativeButton("Nah Dawg", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                        mp.stop();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void login(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.teemo_laugh);
        mp.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Return to the Login Screen")
                .setTitle("Wait!")
                .setPositiveButton("For Sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preferences.edit().clear().apply();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        mp.stop();
                        finish();
                    }
                })
                .setNegativeButton("Nah Dawg", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                        mp.stop();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}