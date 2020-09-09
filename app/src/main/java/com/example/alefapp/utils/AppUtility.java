package com.example.alefapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;

public class AppUtility {

    public AppUtility(){ }

    public boolean isTabletDevice(Context activityContext) {
        boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);
        if (xlarge) {
            DisplayMetrics metrics = activityContext.getResources().getDisplayMetrics();

            return metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH;
        }
        return false;
    }

    public Bitmap scaleBitmapToDisplayWidth(Context context, Bitmap bitmap){
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int bitmapWidth = bitmap.getWidth();
        float scale = 1.0f/(((float)bitmapWidth)/displayWidth);
        return Bitmap.createScaledBitmap(bitmap, displayWidth, (int)(bitmap.getHeight()*scale),false);
    }
}
