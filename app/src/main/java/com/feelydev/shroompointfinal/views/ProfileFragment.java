package com.feelydev.shroompointfinal.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.feelydev.shroompointfinal.adapters.ProfileViewModel;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.feelydev.shroompointfinal.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    SharedPreferences preferences;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        preferences = this.getActivity().getSharedPreferences(Credentials.PREF_FILE_NAME, Context.MODE_PRIVATE);
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        final TextView txtUsername = binding.txtUserName;
        final ImageView imgUserProf = binding.txtUserAvatar;


        textView.setText((CharSequence) profileViewModel.getText());
        Log.v("User", preferences.getString("userName", "") + " is cool");
        txtUsername.setText(preferences.getString("userName", ""));
        Glide.with(getContext())
                .load(Credentials.BASE_PROF_ICON_URL + profileViewModel.getRng().toString() + ".jpg")
                .into(imgUserProf);


        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}