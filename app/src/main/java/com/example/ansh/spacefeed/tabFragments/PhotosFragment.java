package com.example.ansh.spacefeed.tabFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnFlingListener;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.fragments.PhotoDetailFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;
import com.example.ansh.spacefeed.utils.NetworkState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosFragment extends Fragment {

    /**
     * Private member variables
     */
    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;

    public static final String TAG_PHOTO_FRAGMENT = PhotosFragment.class.getSimpleName();

    // Layout related instances.
    @BindView(R.id.f_p_toolbar) Toolbar mToolbar;

    // Private Member Variables
    @BindView(R.id.f_p_recyclerView) RecyclerView mRecyclerView;
    //    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private SnapHelper mSnapHelper;

    private PhotoPagedListAdapter mPhotoPagedListAdapter;
    private CustomViewModel mPhotoViewModel;

    private Fragment mPhotoDetailFragment;

    private String mSort;

    private Unbinder mUnbinder;


    public PhotosFragment() {
        // Required empty public constructor
    }

    public static PhotosFragment newInstance(String sort) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString("sort", sort);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoDetailFragment = new PhotoDetailFragment();
        mSort = getArguments().getString("sort", "latest");
        setHasOptionsMenu(true);

        // getting our CustomViewModel
        mPhotoViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mPhotoViewModel.setPhotoSortOrder(mSort);

        Log.i(TAG_PHOTO_FRAGMENT, "onCreate: called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Photos");

        Log.i(TAG_PHOTO_FRAGMENT, "onCreateView: called");

        // SetUp RecyclerView
        setUpRecyclerView(container.getContext());

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Photo splashResponse = mPhotoViewModel.getPhotoPagedList().getValue().get(pos);
                Log.i(TAG_PHOTO_FRAGMENT, "onClick: " + splashResponse);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Photo", splashResponse);
                mPhotoDetailFragment.setArguments(bundle);

                TransitionSet enterTransitionSet = new TransitionSet();
                enterTransitionSet.addTransition(TransitionInflater.from(container.getContext()).inflateTransition(android.R.transition.move));
                enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
                enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
                mPhotoDetailFragment.setSharedElementEnterTransition(enterTransitionSet);

                ((MainActivity)getActivity()).pushFragment(mPhotoDetailFragment);
            }
        };

        // Creating the Adapter
        mPhotoPagedListAdapter = new PhotoPagedListAdapter(simpleOnItemClickListener);

        // observing the mCollectionsPagedList from view model
        mPhotoViewModel.mPhotoPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashResponse) {
                // in case of any changes submitting the items to adapter
                mPhotoPagedListAdapter.submitList(unSplashResponse);
                Log.i(TAG_PHOTO_FRAGMENT, "onChanged: called " + unSplashResponse.size());
            }
        });

        mPhotoViewModel.mNetworkStateLiveData.observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mPhotoPagedListAdapter.setNetworkState(networkState);
                Log.i(TAG_PHOTO_FRAGMENT, "onChanged: " + "Network State Changed");
            }
        });

        // Setting the adapter
        mPhotoPagedListAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mPhotoPagedListAdapter);

        return view;
    }

    private void setUpRecyclerView(Context context) {
//        mRecyclerView.onScrolled(0, 1);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        SimpleItemAnimator simpleItemAnimator = (SimpleItemAnimator) mRecyclerView.getItemAnimator();
        simpleItemAnimator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.photo_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG_PHOTO_FRAGMENT, "onActivityCreated: called");
    }


}
