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

public class DetailActivity extends AppCompatActivity implements BottomSheetListener, OnClickListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context mContext = DetailActivity.this;

    private BottomSheetDialog mBottomSheetDialog;

    private ImageView mCoverImageView;
    private TextView mBio;
    private CircleImageView mProImageView;
    private TextView mName;

    private RelativeLayout mProImageBackground;

    private String imageUrl;
    private String bio;

    private Toolbar mToolbar;

//    private String mFilePath;
//    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // This piece of code is used to remove the status bar from the activity.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // getting intent from the MainActivity.
//        imageUrl = getIntent().getStringExtra("imageUrl");

        mCoverImageView = findViewById(R.id.cover_image);
        mBio = findViewById(R.id.merge_bio_desc);
        mProImageView = findViewById(R.id.merge_pro_pic);
        mName = findViewById(R.id.merge_pro_name);
        mProImageBackground = findViewById(R.id.merge_proPic_layout);
        mToolbar = findViewById(R.id.transparentToolbar);

        LinearLayout linear = findViewById(R.id.merge_rootLayout);
        linear.setVisibility(View.GONE);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Photo photo = getIntent().getParcelableExtra("Photo");
        imageUrl = photo.getUrls().getRegularUrl();
        bio = photo.getUser().getBio();
        Log.i(TAG, "onCreate: " + imageUrl +" " + bio);


        mName.setText(photo.getUser().getName());
        Log.i(TAG, "onCreate: " + photo.getUser().getName());
        if(bio == null) {
            mBio.setText(R.string.default_no_bio);
        } else {
            mBio.setText(bio);
        }

//        mProImageBackground.setBackgroundColor(Color.parseColor(photo.getColor()));
        mProImageView.setBorderColor(Color.parseColor(photo.getColor()));

        Glide.with(mContext).load(photo.getUser().getProfileImage().getLarge()).into(mProImageView);

        // Loads the clicked image.
        Glide.with(mContext).load(imageUrl)
                .apply(new RequestOptions()
//                        .placeholder(android.R.color.white)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                ).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        supportPostponeEnterTransition();
//                        postponeEnterTransition();
                        linear.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mCoverImageView);

        // onLongClickListener for the imageView to show the bottom sheet dialog fragment.
        mCoverImageView.setOnLongClickListener(v -> {
            showBottomSheetDialog();
            return true;
        });

//        mProImageBackground.setOnClickListener(this);

    }

    // Method to create & show the bottom sheet dialog fragment.
    private void showBottomSheetDialog() {
        BottomSheetFragment sheetFragment = new BottomSheetFragment();
        sheetFragment.show(getSupportFragmentManager(), sheetFragment.getTag());
    }

    /**
     * Implemented method of the "BottomSheetListener"
     * it gets the id from the interface and check which button got clicked.
      */
    @Override
    public void onButtonClicked(int id) {
        if(id == R.id.fragment_history_bottom_sheet_edit) {
            Toast.makeText(mContext, "EDIT CLICKED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "DELETE CLICKED", Toast.LENGTH_SHORT).show();
        }
    }

    // Not using this right now.
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.merge_proPic_layout) {
            UserProfileFragment userProfileFragment = UserProfileFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, userProfileFragment).commit();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
