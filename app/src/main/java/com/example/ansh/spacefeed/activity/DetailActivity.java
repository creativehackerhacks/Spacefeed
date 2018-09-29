package com.example.ansh.spacefeed.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment.BottomSheetListener;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.fragments.UserProfileFragment;
import com.example.ansh.spacefeed.pojos.Photo;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context mContext = DetailActivity.this;


    private Toolbar mToolbar;
    private ImageView mPhotoView, mProPicView;
    private TextView mNameView, mBioView;
    private RelativeLayout mMergeProPicLayout;


    private Photo mPhoto;
    private String mPhotoData, mProPicData, mNameData, mBioData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        showFullScreen();

        mPhoto = getIntent().getParcelableExtra("Photo");

        // Initialize views
        mToolbar = findViewById(R.id.transparentToolbar);
        mPhotoView = findViewById(R.id.detail_photo);
        mProPicView = findViewById(R.id.merge_pro_pic);
        mNameView = findViewById(R.id.merge_pro_name);
        mBioView = findViewById(R.id.merge_bio_desc);

        // set up Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the data
        mPhotoData = mPhoto.getUrls().getRegularUrl();
        mProPicData = mPhoto.getUser().getProfileImage().getLarge();
        mNameData = mPhoto.getUser().getName();
        mBioData = mPhoto.getUser().getBio();

        // Load Photo First.
        Glide.with(mContext).load(mPhotoData)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                )
                .into(mPhotoView);

        // Load Pro Pic.
        Glide.with(mContext).load(mProPicData)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                )
                .into(mProPicView);


        // Load name and bio too.
        mNameView.setText(mNameData);
        if(mBioData != null) {
            mBioView.setText(mBioData);
        } else {
            mBioView.setText("---------");
        }

    }

    private void showFullScreen() {
        // This piece of code is used to remove the status bar from the activity.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
