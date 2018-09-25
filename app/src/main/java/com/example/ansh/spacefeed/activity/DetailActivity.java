package com.example.ansh.spacefeed.activity;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment.BottomSheetListener;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.pojos.Photo;

public class DetailActivity extends AppCompatActivity implements BottomSheetListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private Context mContext = DetailActivity.this;

    private BottomSheetDialog mBottomSheetDialog;

    private ImageView mCoverImageView;
    private TextView mBio;

    private String imageUrl;
    private String bio;

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

        Photo photo = getIntent().getParcelableExtra("Photo");
        imageUrl = photo.getUrls().getRegularUrl();
        bio = photo.getUser().getBio();
        Log.i(TAG, "onCreate: " + imageUrl +" " + bio);

        mCoverImageView = findViewById(R.id.cover_image);
        mBio = findViewById(R.id.merge_bio_desc);

        if(bio == null) {
            mBio.setText("There is no bio to show");
        } else {
            mBio.setText(bio);
        }

        // Loads the clicked image.
        Glide.with(mContext).load(imageUrl)
                .apply(new RequestOptions()
//                        .placeholder(android.R.color.white)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontTransform()
                )
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        supportPostponeEnterTransition();
//                        postponeEnterTransition();
//                        return false;
//                    }
//                })
//                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mCoverImageView);

        // onLongClickListener for the imageView to show the bottom sheet dialog fragment.
        mCoverImageView.setOnLongClickListener(v -> {
            showBottomSheetDialog();
            return true;
        });

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
}
