package com.feelydev.shroompointfinal.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtility {
    public static int calculateNumberOfColumns(Context context, float columnWidthDp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return ((int) (screenWidthDp / columnWidthDp + 0.5));
    }
}
