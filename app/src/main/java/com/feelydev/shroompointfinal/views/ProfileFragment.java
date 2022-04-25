package com.feelydev.shroompointfinal.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.feelydev.shroompointfinal.utils.Credentials;

public class ProfileFragment extends Fragment {

    SharedPreferences preferences;
    private FragmentProfileBinding binding;
    private String username;
    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        preferences = this.getActivity().getSharedPreferences(Credentials.PREF_FILE_NAME, Context.MODE_PRIVATE);
        username = preferences.getString("userName", "");
        email = preferences.getString("email", "");
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
            View root = binding.getRoot();

            final ImageButton editBio = binding.btnEdit;
            final TextView txtUsername = binding.txtUserName;
            final ImageView imgUserProf = binding.imgUserAvatar;

            if (username == null) {
                txtUsername.setText(email);
            } else {
                txtUsername.setText(username);
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


            return root;
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

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        Toast.makeText(getContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
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