package com.example.ansh.spacefeed.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.DetailActivity;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.tabFragments.PhotosFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailPhotoFragment extends Fragment {

    private static final String TAG = DetailPhotoFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private RelativeLayout mMergeProPicLayout;
    private Fragment mUserProfileFragment;

    private ImageView mCoverImageView;
    private CircleImageView mProImageView;
    private TextView mName, mBio;
    private Photo mPhoto;

    public DetailPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static DetailPhotoFragment newInstance() {
        DetailPhotoFragment fragment = new DetailPhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable("PhotoResponse");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_photo, container, false);

        Bundle detailPhoto = new Bundle();
        mUserProfileFragment = UserProfileFragment.newInstance();
        detailPhoto.putParcelable("photoProfile", mPhoto);
        mUserProfileFragment.setArguments(detailPhoto);

        mToolbar = view.findViewById(R.id.transparentToolbar);
        mMergeProPicLayout = view.findViewById(R.id.merge_proPic_layout);
        mCoverImageView = view.findViewById(R.id.detail_fragment_photo);
        mProImageView = view.findViewById(R.id.merge_pro_pic);
        mName = view.findViewById(R.id.merge_pro_name);
        mBio = view.findViewById(R.id.merge_bio_desc);

        ((DetailActivity) getActivity()).setSupportActionBar(mToolbar);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        String coverImageUrl = mPhoto.getUrls().getRegularUrl();
        String profileImageUrl = mPhoto.getUser().getProfileImage().getMedium();
        String name = mPhoto.getUser().getName();
        String description = mPhoto.getUser().getBio();

        Glide.with(getContext()).load(coverImageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mCoverImageView);
        Glide.with(getContext()).load(profileImageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mProImageView);
        mName.setText(name);
        mBio.setText(description);

        mMergeProPicLayout.setOnClickListener(v -> {
            ((DetailActivity) getActivity()).detailPushFragment(mUserProfileFragment);
        });

        return view;
    }

}
