package com.example.ansh.spacefeed.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.childFragments.UserCollectionsFragment;
import com.example.ansh.spacefeed.childFragments.UserLikesFragment;
import com.example.ansh.spacefeed.childFragments.UserPhotoFragment;
import com.example.ansh.spacefeed.pojos.Photo;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    private CircleImageView mUserProPic;
    private TextView mName;
    private Photo mPhoto;

    private FragmentPagerItemAdapter mFragmentPagerItemAdapter;
    private FragmentPagerItems mFragmentPagerItems;
    private ViewPager mViewPager;
    private SmartTabLayout mSmartTabLayout;

    private String mUsername;
    private String mUserPhotoSortOrder;
    private Bundle mUserPhotoBundle;

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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mViewPager = view.findViewById(R.id.f_u_p_viewPager);
        mSmartTabLayout = view.findViewById(R.id.f_u_p_smartTabLayout);
        mUserProPic = view.findViewById(R.id.f_u_p_circleImageView);

//        mName = view.findViewById(R.id.user_profile_pro_name);

        String proImageUrl = mPhoto.getUser().getProfileImage().getLarge();
//        String name = mPhoto.getUser().getName();;
//
        Glide.with(getContext()).load(proImageUrl)
                .into(mUserProPic);
//        mName.setText(name);

        initializeFrags(container.getContext(), view);

        return view;
    }

    private void initializeFrags(Context context, View view) {
        mFragmentPagerItems = FragmentPagerItems.with(context)
                .add("Photos", UserPhotoFragment.class, mUserPhotoBundle)
                .add("Likes", UserLikesFragment.class)
                .add("Collections", UserCollectionsFragment.class)
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
