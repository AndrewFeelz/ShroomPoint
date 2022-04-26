package com.feelydev.shroompointfinal.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.models.RegisterdUser;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences preferences;
    private DatabaseReference mDatabase;
    private String userId;
    private String username;
    private String email;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        preferences = getSharedPreferences(Credentials.PREF_FILE_NAME, MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
            userId = user.getUid();
            username = user.getDisplayName();
            email = user.getEmail();


            preferences.edit().putString("userName", username).apply();
            preferences.edit().putString("email", email).apply();
            preferences.edit().putString("userID", userId).apply();
            Log.v("firebase", userId + " is the current id");

            if(username != null && email != null){
                RegisterdUser registerdUser = new RegisterdUser(userId, username, email, "Add bio...", "user");
                mDatabase.child("users").child(userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.v("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.v("firebase", String.valueOf(task.getResult().getValue()));
                            if(task.getResult().getValue() == null){
                                mDatabase.child("users").child(userId).setValue(registerdUser);
                            }
                            RegisterdUser rUser = task.getResult().getValue(RegisterdUser.class);
                            preferences.edit().putString("role", rUser.getRole()).apply();
                            Log.v("bio", rUser.getBio());
                            preferences.edit().putString("bio", rUser.getBio()).apply();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(LoginActivity.this,"Error: " + response.getError().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}