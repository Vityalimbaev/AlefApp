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
        // Verifies if the Generalized Size of the device is XLARGE to be
        // considered a Tablet
        boolean xlarge = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI
        // (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = activityContext.getResources().getDisplayMetrics();
            Activity activity = (Activity) activityContext;

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            // Yes, this is a tablet!
            return metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
                    || metrics.densityDpi == DisplayMetrics.DENSITY_TV
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH;
        }
        // No, this is notFoundIcon a tablet!
        return false;
    }

    public Bitmap scaleBitmapToDisplayWidth(Context context, Bitmap bitmap){
        int displayWidth = context.getResources().getDisplayMetrics().widthPixels;
        int bitmapWidth = bitmap.getWidth();

        float scale = 1.0f/(((float)bitmapWidth)/displayWidth);

        Log.d("ALEFSCALE", "scaleBitmapToDisplayWidth: "+bitmapWidth + " "+ displayWidth);
        return Bitmap.createScaledBitmap(bitmap, displayWidth, (int)(bitmap.getHeight()*scale),false);
    }
}
