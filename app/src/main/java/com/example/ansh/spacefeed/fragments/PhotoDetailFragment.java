package com.example.ansh.spacefeed.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.DetailActivity;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment;
import com.example.ansh.spacefeed.dialogs.BottomSheetFragment.BottomSheetListener;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.tabFragments.CollectionsFragment;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment implements
        RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    private static final String TAG = PhotoDetailFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;
    private RelativeLayout mMergeProPicLayout;
    private Fragment mUserProfileFragment;

    private ImageView mCoverImageView;
    private CircleImageView mProImageView;
    private TextView mName, mBio;

    private RapidFloatingActionLayout mRapidFloatingActionLayout;
    private RapidFloatingActionButton mRapidFloatingActionButton;
    private RapidFloatingActionContentLabelList mRapidFloatingActionContentLabelList;
    private List<RFACLabelItem> mRFACLabelItems = new ArrayList<>();
    private RapidFloatingActionHelper mRapidFloatingActionHelper;

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
        mUserProfileFragment = UserProfileFragment.newInstance();
        detailPhoto.putParcelable("photoProfile", mPhoto);
        mUserProfileFragment.setArguments(detailPhoto);

        mToolbar = view.findViewById(R.id.transparentToolbar);
        mMergeProPicLayout = view.findViewById(R.id.merge_proPic_layout);
        mCoverImageView = view.findViewById(R.id.detail_fragment_photo);
        mProImageView = view.findViewById(R.id.merge_pro_pic);
        mName = view.findViewById(R.id.merge_pro_name);
        mBio = view.findViewById(R.id.merge_bio_desc);

        mRapidFloatingActionLayout = view.findViewById(R.id.f_d_p_rapid_floating_layout);
        mRapidFloatingActionButton = view.findViewById(R.id.f_d_p_rapid_floating_button);


        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);


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
            ((MainActivity) getActivity()).pushFragment(mUserProfileFragment);
        });

        mToolbar.setNavigationOnClickListener(v ->
                {
                    getActivity().onBackPressed();
                }
        );

        setRapidViews(getContext());

        return view;
    }

    private void setRapidViews(Context context) {
        mRapidFloatingActionContentLabelList = new RapidFloatingActionContentLabelList(context);
        mRapidFloatingActionContentLabelList.setOnRapidFloatingActionContentLabelListListener(this);

        mRFACLabelItems.add(new RFACLabelItem<Integer>()
                .setLabel("Name: Spacefeed")
                .setResId(R.mipmap.ic_launcher)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setWrapper(0)
        );

        mRapidFloatingActionContentLabelList
                .setItems(mRFACLabelItems)
                .setIconShadowRadius(RFABTextUtil.dip2px(context, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(RFABTextUtil.dip2px(context, 5));

        mRapidFloatingActionHelper = new RapidFloatingActionHelper(
                context,
                mRapidFloatingActionLayout,
                mRapidFloatingActionButton,
                mRapidFloatingActionContentLabelList
        ).build();
    }


    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {

    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {

    }


}