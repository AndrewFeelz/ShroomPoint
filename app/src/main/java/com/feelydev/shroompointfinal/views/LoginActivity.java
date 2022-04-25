package com.feelydev.shroompointfinal.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = getSharedPreferences(Credentials.PREF_FILE_NAME, MODE_PRIVATE);
        super.onCreate(savedInstanceState);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build()
        );

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.shroom_icon)
                .setTheme(R.style.Theme_ShroomPointFinal)
                .build();
        signInLauncher.launch(signInIntent);

    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            preferences.edit().putString("userName", user.getDisplayName()).apply();
            preferences.edit().putString("email", user.getEmail()).apply();
            preferences.edit().putString("userID", user.getUid()).apply();

            String test = preferences.getString("userName", "");
            String test2 = preferences.getString("email", "");

            
            Log.v("User", test + " hi");
            Log.v("User", test2 + " hi");
            // ...
        } else {
            Toast.makeText(LoginActivity.this,"Error: " + response.getError().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}