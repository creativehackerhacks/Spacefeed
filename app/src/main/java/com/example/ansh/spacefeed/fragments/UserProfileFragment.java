package com.example.ansh.spacefeed.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.childFragments.UserCollectionsFragment;
import com.example.ansh.spacefeed.childFragments.UserLikesFragment;
import com.example.ansh.spacefeed.childFragments.UserPhotoFragment;
import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.pojos.User;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    private Toolbar mToolbar;

    private ApiClient mApiClient;
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";

    private User mUser;

    private ImageView mBlurLinearLayout;
    private CircleImageView mUserProPic;
    private TextView mName, mDesc;
    private Photo mPhoto;
    private LinearLayout mUserPortfolioLinearLayout;
    private ImageButton mUserPortfolioInsta;

    private FragmentPagerItemAdapter mFragmentPagerItemAdapter;
    private FragmentPagerItems mFragmentPagerItems;
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;

    private String mUsername;
    private String mUserPhotoSortOrder;

    private Bundle mUserPhotoBundle;
    private Bundle mUserLikesBundle;
    private Bundle mUserCollectionsBundle;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable("photoProfile");
            mUsername = getArguments().getString("username");
            mUserPhotoSortOrder = getArguments().getString("userPhotoSortOrder");
        }

        mUserPhotoBundle = new Bundle();
        mUserPhotoBundle.putString("username", mUsername);
        mUserPhotoBundle.putString("userPhotoSortOrder", mUserPhotoSortOrder);

        mUserLikesBundle = new Bundle();
        mUserLikesBundle.putString("username", mUsername);
        mUserLikesBundle.putString("userLikesSortOrder", mUserPhotoSortOrder);

        mUserCollectionsBundle = new Bundle();
        mUserCollectionsBundle.putString("username", mUsername);

        mApiClient = ApiClient.getInstance();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mToolbar = view.findViewById(R.id.f_u_p_toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mPhoto.getUser().getUsername());

        mBlurLinearLayout = view.findViewById(R.id.f_u_p_blur_bg);
        mViewPager = view.findViewById(R.id.f_u_p_viewPager);
        mSmartTabLayout = view.findViewById(R.id.f_u_p_smartTabLayout);
        mUserProPic = view.findViewById(R.id.f_u_p_circleImageView);
        mUserPortfolioLinearLayout = view.findViewById(R.id.f_u_p_user_portfolio);
        mUserPortfolioInsta = view.findViewById(R.id.f_u_p_user_portfolio_insta);

        initializeToolbar(container.getContext());

        mName = view.findViewById(R.id.f_u_p_name);
        mDesc = view.findViewById(R.id.f_u_p_desc);

        String proImageUrl = mPhoto.getUser().getProfileImage().getLarge();
        String name = mPhoto.getUser().getName();
        String desc = mPhoto.getUser().getBio();
//
        Glide.with(getContext()).load(proImageUrl)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(20, 16)))
                .into(mBlurLinearLayout);

        Glide.with(getContext()).load(proImageUrl)
                .into(mUserProPic);
        mName.setText(name);
//
        if(desc != null) {
            mDesc.setText(desc);
            mDesc.setVisibility(View.VISIBLE);
        }

        initializeFrags(container.getContext(), view);

        mToolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        mUserPortfolioInsta.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUser.getPortfolioUrl()));
            startActivity(intent);
        });

        return view;
    }

    private void initializeToolbar(Context context) {
        mApiClient.getApi().getUser(mUsername, CLIENT_ID)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response != null && response.code() == 200) {
                            mUser = response.body();
                            Log.i("FINE", "Portfolio: " + mUser.getPortfolioUrl());
                            isPortfolioAvialable(mUser);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    private void isPortfolioAvialable(User user) {
        if(user.getPortfolioUrl() != null) {
            mUserPortfolioLinearLayout.setVisibility(View.VISIBLE);
            mUserPortfolioInsta.setVisibility(View.VISIBLE);
        }
    }

    private void initializeFrags(Context context, View view) {
        mFragmentPagerItems = FragmentPagerItems.with(context)
                .add("Photos", UserPhotoFragment.class, mUserPhotoBundle) // removed bundle to avoid request from api
                .add("Likes", UserLikesFragment.class, mUserLikesBundle) // removed bundle to avoid request from api
                .add("Collections", UserCollectionsFragment.class, mUserCollectionsBundle) // removed bundle to avoid request from api
                .create();

        mFragmentPagerItemAdapter = new FragmentPagerItemAdapter(getChildFragmentManager(), mFragmentPagerItems);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mFragmentPagerItemAdapter);
        mViewPager.setCurrentItem(0);

        mSmartTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getActivity().invalidateOptionsMenu();
        menu.clear();
    }

}
