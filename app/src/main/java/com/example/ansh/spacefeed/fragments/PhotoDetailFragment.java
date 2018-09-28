package com.example.ansh.spacefeed.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.DetailActivity;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment.BottomSheetListener;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.tabFragments.CollectionsFragment;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment implements BottomSheetListener {

    private static final String TAG = PhotoDetailFragment.class.getSimpleName();
    private Context mContext = getContext();

    private BottomSheetDialog mBottomSheetDialog;

    private ImageView mCoverImageView;
    private TextView mBio;
    private CircleImageView mProImageView;
    private TextView mName;

    private LinearLayout mProImageBackground;

    private String imageUrl;
    private String bio;

    private Photo mPhoto;

//    private String mFilePath;
//    private Toolbar mToolbar;


    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CollectionsFragment.
     */
    public static PhotoDetailFragment newInstance() {
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This piece of code is used to remove the status bar from the activity.
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_detail, container, false);


//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // getting intent from the MainActivity.
//        imageUrl = getIntent().getStringExtra("imageUrl");

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            mPhoto = bundle.getParcelable("Photo");
        }

        imageUrl = mPhoto.getUrls().getRegularUrl();
        bio = mPhoto.getUser().getBio();
        Log.i(TAG, "onCreate: " + imageUrl +" " + bio);

        mCoverImageView = view.findViewById(R.id.cover_image);
        mBio = view.findViewById(R.id.merge_bio_desc);
        mProImageView = view.findViewById(R.id.merge_pro_pic);
        mName = view.findViewById(R.id.merge_pro_name);
        mProImageBackground = view.findViewById(R.id.merge_proPic_layout);

        mName.setText(mPhoto.getUser().getName());
        if(bio == null) {
            mBio.setText(R.string.default_no_bio);
        } else {
            mBio.setText(bio);
        }

        mProImageBackground.setBackgroundColor(Color.parseColor(mPhoto.getColor()));

        Glide.with(getContext()).load(mPhoto.getUser().getProfileImage().getLarge()).into(mProImageView);

        // Loads the clicked image.
        Glide.with(getContext()).load(imageUrl)
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



        return view;
    }


    // Method to create & show the bottom sheet dialog fragment.
    private void showBottomSheetDialog() {
        BottomSheetFragment sheetFragment = new BottomSheetFragment();
        sheetFragment.show(getActivity().getSupportFragmentManager(), sheetFragment.getTag());
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
