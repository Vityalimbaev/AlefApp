package com.example.alefapp.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.alefapp.fragments.ImageFragment;
import com.example.alefapp.fragments.ListImagesFragment;
import com.example.alefapp.R;

public class MainActivity extends AppCompatActivity implements ListImagesFragment.OnClickImageListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            ListImagesFragment listImagesFragment = new ListImagesFragment();
            listImagesFragment.setOnClickImageListener(this);
            startFragment(listImagesFragment, false, ListImagesFragment.FRAGMENT_TAG);
        }else{
            ListImagesFragment fragment = (ListImagesFragment) getSupportFragmentManager().findFragmentByTag(ListImagesFragment.FRAGMENT_TAG);
            if(fragment != null){
                fragment.setOnClickImageListener(this);
            }
        }
    }

    private <T extends Fragment> void startFragment(T fragment, boolean addToBackStack, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        if(addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickImage(String urlString) {
        startFragment(new ImageFragment(urlString), true, ImageFragment.FRAGMENT_TAG);
    }
}
