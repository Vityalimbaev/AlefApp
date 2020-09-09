package com.example.alefapp.fragments;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.alefapp.utils.AppUtility;
import com.example.alefapp.R;

public class ImageFragment extends Fragment {
    public static final String FRAGMENT_TAG = "imgFrag";
    public final String IMG_URL_KEY = "imgUrlKey";
    public String url;

    public ImageFragment(String url) {
        this.url = url;
    }

    public ImageFragment(){
        this(null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
           url =  savedInstanceState.getString(IMG_URL_KEY, null);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null, false);
        loadImage(view);
        return view;
    }

    private void loadImage(final View view) {
        if(url != null) {
            final ImageView imgview = view.findViewById(R.id.fullScreenImageView);
            Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            imgview.setImageBitmap(new AppUtility().scaleBitmapToDisplayWidth(view.getContext(),resource));
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(IMG_URL_KEY, url);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
