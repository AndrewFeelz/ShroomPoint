package com.feelydev.shroompointfinal.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.adapters.ProfileViewModel;
import com.feelydev.shroompointfinal.databinding.FragmentProfileBinding;
import com.feelydev.shroompointfinal.models.RegisterdUser;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private DatabaseReference mDatabase;
    //Preferences
    SharedPreferences preferences;
    private String username;
    private String email;
    private String userId;
    private String role;
    private String bio;
    //inflation and fragment wide access widgets
    private FragmentProfileBinding binding;
    private ImageButton editBio, editUsers;
    private TextView txtBio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        preferences = this.getActivity().getSharedPreferences(Credentials.PREF_FILE_NAME, Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        username = preferences.getString("userName", "");
//        Toast.makeText(getContext(), "Hello " + username, Toast.LENGTH_LONG).show();
        email = preferences.getString("email", "");
        userId = preferences.getString("userID", "");
        role = preferences.getString("role", "");
        bio = preferences.getString("bio", "");
        if (username.equals("") && email.equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("You are a guest, login to access your profile page.")
                    .setTitle("Wait!")
                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            preferences.edit().clear().apply();
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

            binding = FragmentProfileBinding.inflate(inflater, container, false);
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            View root = binding.getRoot();

            editBio = (ImageButton) rootView.findViewById(R.id.btnEdit);


            editUsers = (ImageButton) rootView.findViewById(R.id.btnAdmin);
            txtBio = (TextView) rootView.findViewById(R.id.txtBioInfo);
            Log.v("bio", bio + "from frag");
            txtBio.setText(bio);
            final TextView txtUsername = (TextView) rootView.findViewById(R.id.txtUserName);
            final ImageView imgUserProf = (ImageView) rootView.findViewById(R.id.imgUserAvatar);

            if (username == null) {
                txtUsername.setText(email);
            } else {
                txtUsername.setText(username);
            }

            if (!role.equals("admin")){
                editUsers.setVisibility(View.INVISIBLE);
            } else {
                editUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Are you sure you want to NUKE THE DATABASE?")
                                .setTitle("Wait!")
                                .setPositiveButton("End my Suffering", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                mDatabase.removeValue();
                                    }
                                })
                                .setNegativeButton("Nah Dawg", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Toast.makeText(getContext(), "Crisis Averted", Toast.LENGTH_LONG).show();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }


            Glide.with(getContext())
                    .load(Credentials.BASE_PROF_ICON_URL + profileViewModel.getRng().toString() + ".jpg")
                    .into(imgUserProf);

            editBio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialogEditBio();
                }
            });

            return rootView;
        }
        return null;
    }

    private void alertDialogEditBio() {
        // get alert_dialog.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set alert_dialog.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);
        mDatabase.child("users").child(userId).child("bio").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String bioText = String.valueOf(task.getResult().getValue());
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    userInput.setText(bioText);
                }
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        Toast.makeText(getContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                        mDatabase.child("users").child(userId).child("bio").setValue(userInput.getText().toString());
                        txtBio.setText(userInput.getText().toString());
                        preferences.edit().putString("bio", userInput.getText().toString()).apply();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}