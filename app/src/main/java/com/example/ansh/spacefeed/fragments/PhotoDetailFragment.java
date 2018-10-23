package com.example.ansh.spacefeed.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;

import java.io.File;
import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment {

    private static final String TAG = PhotoDetailFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private RelativeLayout mMergeProPicLayout;
    private Fragment mUserProfileFragment;

    private ImageView mCoverImageView;
    private CircleImageView mProImageView;
    private TextView mName, mBio;
    private Button mDetailProfileFollowButton;
//    File mFile;
    String mPath;

    private ApiClient mApiClient;
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    private static WeakReference<Fragment> instance;

    private Photo mPhoto;

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
        PhotoDetailFragment photoDetailFragment = new PhotoDetailFragment();
        Bundle args = new Bundle();
        photoDetailFragment.setArguments(args);
        return photoDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable("Photo");
            Log.i(TAG, "onCreate: " + mPhoto);
        }

//        mFile = new File(getContext().getFilesDir(), "space");
//        mPath = mFile.getAbsolutePath() + "/Space";

        // This piece of code is used to remove the status bar from the activity.
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_photo, container, false);

        Bundle detailPhoto = new Bundle();
        mUserProfileFragment = new UserProfileFragment();
        detailPhoto.putParcelable("photoProfile", mPhoto);
        mUserProfileFragment.setArguments(detailPhoto);

        mToolbar = view.findViewById(R.id.transparentToolbar);
        mMergeProPicLayout = view.findViewById(R.id.merge_proPic_layout);
        mCoverImageView = view.findViewById(R.id.detail_fragment_photo);
        mProImageView = view.findViewById(R.id.merge_pro_pic);
        mName = view.findViewById(R.id.merge_pro_name);
        mBio = view.findViewById(R.id.merge_bio_desc);
        mDetailProfileFollowButton = view.findViewById(R.id.detail_profile_follow_button);

        mApiClient = ApiClient.getInstance();

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


        String coverImageUrl = mPhoto.getUrls().getRegularUrl();
        String profileImageUrl = mPhoto.getUser().getProfileImage().getMedium();
        String name = mPhoto.getUser().getName();
        String description = mPhoto.getUser().getBio();

        Glide.with(container.getContext()).load(coverImageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mCoverImageView);
        Glide.with(container.getContext()).load(profileImageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mProImageView);
        mName.setText(name);
        mBio.setText(description);


        mMergeProPicLayout.setOnClickListener(v -> {
            ((MainActivity) getActivity()).pushFragment(mUserProfileFragment);
        });

        mToolbar.setNavigationOnClickListener(v ->
                {
                    getActivity().onBackPressed();
                }
        );

        mDetailProfileFollowButton.setOnClickListener(v -> {
            Log.i(TAG, "onCreateView: Profile-- " + mPhoto.getId());
            mApiClient.getApi().getDownload(mPhoto.getId())
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.i(TAG, "onResponse: Download " + response);
                            String imageUri = response.toString();
//                            Log.i(TAG_PHOTO_FRAGMENT, "onResponse: sss " + mFile);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) getActivity()).SetNavigationVisibiltity(false);
    }

    @Override
    public void onStop() {
        super.onStop();
//        ((MainActivity) getActivity()).SetNavigationVisibiltity(true);
    }

}