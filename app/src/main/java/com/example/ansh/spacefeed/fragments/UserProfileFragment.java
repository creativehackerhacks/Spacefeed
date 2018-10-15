package com.example.ansh.spacefeed.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.pojos.Photo;

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

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserProfileFragment.
     */
    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhoto = getArguments().getParcelable("photoProfile");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        mUserProPic = view.findViewById(R.id.user_profile_pro_pic);
        mName = view.findViewById(R.id.user_profile_pro_name);

        String proImageUrl = mPhoto.getUser().getProfileImage().getLarge();
        String name = mPhoto.getUser().getName();;

        Glide.with(getContext()).load(proImageUrl)
                .into(mUserProPic);
        mName.setText(name);

        return view;
    }

}
