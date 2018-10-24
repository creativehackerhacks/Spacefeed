package com.example.ansh.spacefeed.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.utils.ImageSaveTask;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

    @BindView(R.id.f_d_p_toolbar) Toolbar mToolbar;
    @BindView(R.id.merge_proPic_layout) RelativeLayout mMergeProPicLayout;
    @BindView(R.id.detail_fragment_photo) ImageView mCoverImageView;
    @BindView(R.id.merge_pro_pic) CircleImageView mProImageView;
    @BindView(R.id.merge_pro_name) TextView mName;
    @BindView(R.id.merge_bio_desc) TextView mDesc;
    @BindView(R.id.detail_profile_follow_button) Button mDetailProfileFollowButton;

    private FloatingActionButton mFloatingActionButton;
    private Fragment mUserProfileFragment;
    private Unbinder mUnbinder;

    private ApiClient mApiClient;
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    private static WeakReference<Fragment> instance;

    private String mFilePath = Environment.getExternalStorageDirectory() + "/SpaceFeed";

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

        Log.i(TAG, "onCreate: called---" + this);

        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable("Photo");
            Log.i(TAG, "onCreate: Photo--- " + mPhoto);
        }
        setHasOptionsMenu(true);
//        ((MainActivity) getActivity()).showOverFlowMenu(false);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_photo, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        Log.i(TAG, "onCreateView: called--- " + this);

        Bundle detailPhoto = new Bundle();
        mUserProfileFragment = new UserProfileFragment();
        detailPhoto.putParcelable("photoProfile", mPhoto);
        mUserProfileFragment.setArguments(detailPhoto);

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
                        .priority(Priority.IMMEDIATE)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mCoverImageView);
        Glide.with(container.getContext()).load(profileImageUrl)
                .apply(new RequestOptions()
                        .priority(Priority.HIGH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(mProImageView);
        mName.setText(name);
        mDesc.setText(description);


        mMergeProPicLayout.setOnClickListener(v -> {
            ((MainActivity) getActivity()).pushFragment(mUserProfileFragment);
        });

        mToolbar.setNavigationOnClickListener(v ->
                {
                    getActivity().onBackPressed();
                }
        );

        return view;
    }

    @OnClick(R.id.detail_profile_follow_button)
    public void onProfileFollowButton(View view) {
        Log.i(TAG, "onCreateView: Profile-- " + mPhoto.getId());
        mApiClient.getApi().getDownload(mPhoto.getId(), CLIENT_ID)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i(TAG, "onResponse: Download " + response);
                        String imageUri = response.toString();
                        Log.i(TAG, "onResponse: sss " + mFilePath);
//                        ImageSaveTask imageSaveTask = new ImageSaveTask(getContext());
//                        imageSaveTask.execute(imageUri, mFilePath);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: called.");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: called.");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: called.");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).SetNavigationVisibility(false);
        Log.i(TAG, "onResume: called.");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: called.");
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).SetNavigationVisibility(true);
        Log.i(TAG, "onStop: called.");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: called.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: called.");
        mUnbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: called.");
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getActivity().invalidateOptionsMenu();
        menu.clear();
    }

}