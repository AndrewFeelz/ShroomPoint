package com.feelydev.shroompointfinal.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final String mText;
    private final Integer rng;

    public ProfileViewModel() {
        mText= "This is notifications fragment";

        int random_int = (int)Math.floor(Math.random()*(4787-4777+1)+4777);
        rng = random_int;
    }

    public String getText() {
        return mText;
    }
    public Integer getRng() { return rng; }
}