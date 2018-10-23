package com.example.ansh.spacefeed.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionPhotoFragment extends Fragment {


//    private Context mContext = getActivity();
    public static final String TAG = "FUCK";

    // Layout related instances.
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    // Private Member Variables
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private PhotoPagedListAdapter mPhotoPagedListAdapter;
    private CustomViewModel mPhotoViewModel;

    private CollectionPhoto mCollectionPhoto;

    private ImageView mCollectionCoverPhotoImageView;
    private CircleImageView mCollectionProfilePhotoImageView;
    private TextView mCollectionPhotoUserName;

    private Fragment mPhotoDetailFragment;


    public CollectionPhotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CollectionPhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CollectionPhotoFragment newInstance() {
        CollectionPhotoFragment fragment = new CollectionPhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCollectionPhoto = getArguments().getParcelable("collection");
        }

        Log.i(TAG, "onCreate: " + mCollectionPhoto.getId());

        mPhotoDetailFragment = new PhotoDetailFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewCompat.requestApplyInsets(mCoordinatorLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection_photo, container, false);

        mToolbar = view.findViewById(R.id.collection_photo_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mCollectionPhoto.getTitle());

        mCollapsingToolbarLayout = view.findViewById(R.id.f_c_p_collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(mCollectionPhoto.getTitle());
        mCoordinatorLayout = view.findViewById(R.id.f_c_p_coordinator_layout);

        mRecyclerView = view.findViewById(R.id.collectionPhotoRecyclerView);

        mCollectionCoverPhotoImageView = view.findViewById(R.id.collection_photo_cover);
        mCollectionProfilePhotoImageView = view.findViewById(R.id.f_c_p_profile_image);
        mCollectionPhotoUserName = view.findViewById(R.id.f_c_p_user_name);

        Glide.with(getContext()).load(mCollectionPhoto.getCoverPhoto().getUrls().getRegularUrl()).into(mCollectionCoverPhotoImageView);
        Glide.with(getContext()).load(mCollectionPhoto.getUser().getProfileImage().getLarge()).into(mCollectionProfilePhotoImageView);
        mCollectionPhotoUserName.setText(mCollectionPhoto.getUser().getName());

        // getting our CustomViewModel
        mPhotoViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mPhotoViewModel.setCollectionId(mCollectionPhoto.getId());

        // SetUp
        setUpRecyclerView(container.getContext());

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Photo splashResponse = mPhotoViewModel.mCollectionPhotoPagedList.getValue().get(pos);
                Log.i(TAG, "onClick: " + "Chutia");

//                Intent intent = new Intent(getContext(), DetailActivity.class);
//                intent.putExtra("Photo", splashResponse);
//
////                ActivityOptionsCompat options = ActivityOptionsCompat.
////                        makeSceneTransitionAnimation(MainActivity.this,
////                                v,
////                                ViewCompat.getTransitionName(v));
//
//                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Photo", splashResponse);
                mPhotoDetailFragment.setArguments(bundle);
                ((MainActivity)getActivity()).pushFragment(mPhotoDetailFragment);

            }

        };

        // Creating the Adapter
        mPhotoPagedListAdapter = new PhotoPagedListAdapter(simpleOnItemClickListener);

        // observing the mCollectionsPagedList from view model
        mPhotoViewModel.mCollectionPhotoPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashResponse) {
                // in case of any changes submitting the items to adapter
                mPhotoPagedListAdapter.submitList(unSplashResponse);
                Log.i(TAG, "MAIN ACTIVITY: " + unSplashResponse.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mPhotoPagedListAdapter);


        mToolbar.setNavigationOnClickListener(v->
                { getActivity().onBackPressed(); }
        );

        return view;
    }

    private void setUpRecyclerView(Context context) {
//        int columnSpacingInPixels = 16;
//        mRecyclerView.addItemDecoration(new ColumnSpaceItemDecoration(columnSpacingInPixels));

        mLinearLayoutManager = new LinearLayoutManager(context);
//        mGridLayoutManager = new GridLayoutManager(this, 2);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(null);

//        mRecyclerView.setItemViewCacheSize(20);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

}
