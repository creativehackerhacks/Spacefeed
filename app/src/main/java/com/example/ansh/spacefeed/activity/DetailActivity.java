package com.example.ansh.spacefeed.activity;

import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.fragments.DetailPhotoFragment;
import com.example.ansh.spacefeed.interfaces.BottomNavListener;
import com.example.ansh.spacefeed.pojos.Photo;
import com.ncapdevi.fragnav.FragNavController;

public class DetailActivity extends AppCompatActivity implements BottomNavListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context mContext = DetailActivity.this;
    private Fragment mDetailPhotoFragment;

    private FragNavController.Builder mBuilder;
    private FragNavController mFragNavController;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    boolean isFalse;

    private Photo mPhoto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBuilder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_frame_layout);
        mDetailPhotoFragment = DetailPhotoFragment.newInstance();
        mBuilder.rootFragment(mDetailPhotoFragment);
        mFragNavController = mBuilder.build();

        mPhoto = getIntent().getParcelableExtra("Photo");
//        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();

//        isFalse = true;
        showHide();
        Bundle photoBundle = new Bundle();
        photoBundle.putParcelable("PhotoResponse", mPhoto);
        mDetailPhotoFragment.setArguments(photoBundle);

//        mFragmentTransaction.replace(R.id.main_frame_layout, mDetailPhotoFragment)
////                .addToBackStack(null) // don't add this for first fragment
//                .commit();

    }

    @Override
    public void showHide() {
       if(isFalse) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void detailPushFragment(Fragment fragment) {
        mFragNavController.pushFragment(fragment);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        if(mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onBackPressed() {
        if(mFragNavController.isRootFragment()) {
            super.onBackPressed();
        } else {
            mFragNavController.popFragment();
        }
    }

}
